package com.example.comunicaa.screens.card_management.create_card

import android.net.Uri
import android.view.LayoutInflater
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentCreateCardBinding
import com.example.comunicaa.domain.models.cards.SubCategory
import com.example.comunicaa.screens.card_management.create_card.bottom_sheets.SelectSubcategoryBottomSheet
import com.example.comunicaa.screens.card_management.create_card.dialogs.choose_audio.ChooseAudioDialog
import com.example.comunicaa.screens.card_management.create_card.dialogs.choose_image.ChooseImageProviderDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCardFragment : BaseFragment<FragmentCreateCardBinding>() {

    private var imageUri: Uri? = null
    private var audioPath: String? = null
    private var subcategory: SubCategory? = null

    override val bindingInflater: (LayoutInflater) -> FragmentCreateCardBinding =
        FragmentCreateCardBinding::inflate
    override val viewModel: CreateCardViewModel by viewModels()

    private val navArgs: CreateCardFragmentArgs by navArgs()

    override fun initViews() {
        binding.includeCreateActionHeader.ivCreateCardHeaderBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.etCreateActionName.addTextChangedListener {
            binding.includeCreateActionPreview.tvActionPreview.text = it.toString()
        }

        viewModel.fetchCardData(navArgs.keys)
        setupSelectImage()
        setupSelectAudio()
        setupSelectSubcategory()
    }

    override fun initObservers() {}

    private fun setupSelectImage() {
        binding.includeCreateActionSelectImage.cvCreateActionSelectImage.setOnClickListener {
            val fragment = ChooseImageProviderDialog.newInstance(imageUri)
            fragment.onImageSelected = {
                imageUri = it
                binding.includeCreateActionPreview.ivCreatePreview.setImageURI(imageUri)
                fragment.dismiss()
            }
            fragment.show(childFragmentManager, "chooseImage")
        }
    }

    private fun setupSelectAudio() {
        binding.includeCreateActionSelectAudio.cvCreateActionSelectAudio.setOnClickListener {
            val fragment = ChooseAudioDialog.newInstance(audioPath)
            fragment.onAudioSelected = { path, name ->
                audioPath = path
                binding.includeCreateActionSelectAudio.tvPreselectedAudio.text = name
                fragment.dismiss()
            }
            fragment.show(childFragmentManager, "chooseAudio")
        }
    }

    private fun setupSelectSubcategory() {
        binding.includeCreateActionSelectSubcategory.cvCreateActionSelectSubcategory.setOnClickListener {
            val fragment = SelectSubcategoryBottomSheet.newInstance(subcategory)
            fragment.onSubcategorySelect = {
                binding.includeCreateActionSelectSubcategory.tvCreateActionSelectSubcategory.text = it.name
                subcategory = it
                fragment.dismiss()
            }
            fragment.show(childFragmentManager, fragment.tag)
        }
    }
}