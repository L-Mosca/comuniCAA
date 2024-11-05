package com.example.comunicaa.screens.card_management.create_card

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentCreateCardBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.screens.card_management.create_card.dialogs.audio_permission.AudioPermissionDialog
import com.example.comunicaa.screens.card_management.create_card.dialogs.choose_audio.ChooseAudioDialog
import com.example.comunicaa.screens.card_management.create_card.dialogs.choose_image.ChooseImageProviderDialog
import com.example.comunicaa.utils.hideKeyboard
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@AndroidEntryPoint
class CreateCardFragment : BaseFragment<FragmentCreateCardBinding>() {

    private var imageUri: Uri? = null
    private var imageUrl: String? = null
    private var audioPath: String? = null
    private var audioUrl: String? = null

    //private var subcategory: SubCategory? = null
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var appSettingsLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionDialog: AudioPermissionDialog

    override val bindingInflater: (LayoutInflater) -> FragmentCreateCardBinding =
        FragmentCreateCardBinding::inflate
    override val viewModel: CreateCardViewModel by viewModels()

    private val navArgs: CreateCardFragmentArgs by navArgs()

    override fun initViews() {
        initSettingsLauncher()
        initPermissionLauncher()

        if (!hasRecordAudioPermission()) permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)

        binding.includeCreateActionHeader.ivCreateCardHeaderBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etCreateActionName.addTextChangedListener {
            binding.includeCreateActionPreview.tvActionPreview.text = it.toString()
        }

        viewModel.fetchCardData(navArgs.keys)
        showImagePreview(false)
        setupSelectImage()
        setupSelectAudio()
        //setupSelectSubcategory()
        binding.fabSaveCard.setOnClickListener {
            hideKeyboard()
            val title = binding.etCreateActionName.text.toString()
            viewModel.checkCardData(title, imageUri, audioPath, requireContext().contentResolver)
        }
    }

    override fun initObservers() {
        viewModel.createSuccess.observe(viewLifecycleOwner) {
            showShortSnackBar(getString(R.string.save_success))
            findNavController().popBackStack()
        }

        viewModel.createError.observe(viewLifecycleOwner) {
            showShortSnackBar(getString(R.string.create_action_error))
        }

        viewModel.emptyDataError.observe(viewLifecycleOwner) {
            showShortSnackBar(getString(R.string.empty_card_data_error))
        }

        viewModel.initialCard.observe(viewLifecycleOwner) { setupInitialData(it) }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.includeCreateCardLoading.showLoading(
                it
            )
        }
    }

    private fun setupSelectImage() {
        binding.apply {
            includeCreateActionSelectImage.cvCreateActionSelectImage.setOnClickListener {
                val fragment = ChooseImageProviderDialog.newInstance(imageUri, imageUrl)

                fragment.onImageSelected = {
                    it?.let { image ->
                        showImagePreview(true)
                        includeCreateActionPreview.ivCreatePreview.ivSubcategory.setImageURI(image)
                        imageUri = image

                        val imageName = image.lastPathSegment ?: getString(R.string.select_image)
                        includeCreateActionSelectImage.tvSelectImage.text = imageName
                    }

                    fragment.dismiss()
                }
                fragment.show(childFragmentManager, fragment.tag)
            }
        }
    }

    private fun setupSelectAudio() {
        binding.includeCreateActionSelectAudio.cvCreateActionSelectAudio.setOnClickListener {
            val fragment = ChooseAudioDialog.newInstance(audioPath, audioUrl)
            fragment.onAudioSelected = { path, name ->
                audioPath = path
                name?.let { binding.includeCreateActionSelectAudio.tvPreselectedAudio.text = it }
                fragment.dismiss()
            }
            fragment.show(childFragmentManager, fragment.tag)
        }
    }

    private fun setupInitialData(card: ActionCard) {
        showImagePreview(true)
        imageUrl = card.image
        audioUrl = card.sound

        binding.apply {
            etCreateActionName.setText(card.name)
            includeCreateActionPreview.tvActionPreview.text = card.name
            includeCreateActionSelectAudio.tvPreselectedAudio.text = getAudioName()

            Picasso.get().load(imageUrl)
                .into(includeCreateActionPreview.ivCreatePreview.ivSubcategory,
                    object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            includeCreateActionPreview.ivCreatePreview.piActionLoading.visibility =
                                View.GONE
                        }

                        override fun onError(e: Exception?) {
                            includeCreateActionPreview.ivCreatePreview.piActionLoading.visibility =
                                View.GONE
                        }
                    })
        }
    }

    private fun showImagePreview(show: Boolean) {
        binding.includeCreateActionPreview.apply {
            ivCreatePreview.ivSubcategory.isVisible = show
            tvActionPreview.isVisible = show
            ivCreatePreview.vShadow.isVisible = true
            ivCreatePreview.piActionLoading.isVisible = false
        }
    }

    private fun getAudioName(): String {
        val decodedUrl = URLDecoder.decode(audioUrl, StandardCharsets.UTF_8.toString())
        return decodedUrl.substringAfterLast("/").substringBefore("?")
    }

    private fun showAudioPermissionDialog() {
        permissionDialog = AudioPermissionDialog.newInstance().apply {
            isCancelable = false

            onSettingsPressed = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", requireContext().packageName, null)
                }
                appSettingsLauncher.launch(intent)
                dismiss()
            }
        }
        permissionDialog.show(childFragmentManager, permissionDialog.tag)
    }

    private fun hasRecordAudioPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun initPermissionLauncher() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (!isGranted) showAudioPermissionDialog()
            }
    }

    private fun initSettingsLauncher() {
        appSettingsLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (!hasRecordAudioPermission()) showAudioPermissionDialog()
        }
    }

    /*private fun setupSelectSubcategory() {
        binding.includeCreateActionSelectSubcategory.cvCreateActionSelectSubcategory.setOnClickListener {
            val fragment = SelectSubcategoryBottomSheet.newInstance(subcategory)
            fragment.onSubcategorySelect = {
                binding.includeCreateActionSelectSubcategory.tvCreateActionSelectSubcategory.text = it.name
                binding.includeCreateActionPreview.tvPreviewSubcategory.text = it.name
                subcategory = it
                fragment.dismiss()
            }
            fragment.show(childFragmentManager, fragment.tag)
        }
    }*/
}