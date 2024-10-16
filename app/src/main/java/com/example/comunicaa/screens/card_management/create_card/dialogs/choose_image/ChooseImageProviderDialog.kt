@file:Suppress("DEPRECATION")

package com.example.comunicaa.screens.card_management.create_card.dialogs.choose_image

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseDialog
import com.example.comunicaa.databinding.DialogChooseImageProviderBinding
import com.example.comunicaa.utils.checkCameraPermission
import com.example.comunicaa.utils.checkPickImagePermission
import com.example.comunicaa.utils.createFile
import com.example.comunicaa.utils.getImageUri
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ChooseImageProviderDialog : BaseDialog<DialogChooseImageProviderBinding>() {
    override val bindingInflater: (LayoutInflater) -> DialogChooseImageProviderBinding =
        DialogChooseImageProviderBinding::inflate
    override val viewModel: ChooseImageProviderViewModel by viewModels()

    companion object {

        private const val CHOOSE_IMAGE_DIALOG_EXTRA = "ChooseImageDialog.imageUri"

        fun newInstance(imageUri: Uri?): ChooseImageProviderDialog {
            return ChooseImageProviderDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(CHOOSE_IMAGE_DIALOG_EXTRA, imageUri)
                }
            }
        }
    }

    private lateinit var galleryLauncher: ActivityResultLauncher<PickVisualMediaRequest>
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null
    private lateinit var photoFile: File

    var onImageSelected: ((Uri?) -> Unit)? = null

    override fun getBundleArguments() {
        viewModel.handleInitialData(arguments?.getParcelable(CHOOSE_IMAGE_DIALOG_EXTRA))
    }

    override fun initViews() {
        setupListeners()
        setupLaunchers()
    }

    override fun initObservers() {
        viewModel.imageSuccess.observe(viewLifecycleOwner) { showImage(it) }
        viewModel.imageError.observe(viewLifecycleOwner) { imageError() }
    }

    private fun setupListeners() {
        binding.apply {
            rlChooseImage.setOnClickListener { dismiss() }
            cvChooseImage.setOnClickListener { }
            vDeleteImage.setOnClickListener { deleteImage() }
            btCamera.setOnClickListener { requireActivity().checkCameraPermission { openCamera() } }
            btGallery.setOnClickListener { requireActivity().checkPickImagePermission { openGallery() } }
            ivConfirmImage.setOnClickListener { onImageSelected?.invoke(imageUri) }
        }
    }

    private fun setupLaunchers() {
        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                viewModel.handleCameraImage(imageUri, result)
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                viewModel.handleGalleryImage(uri)
            }
    }

    private fun openCamera() {
        photoFile = createFile()
        imageUri = getImageUri(photoFile)
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        requireActivity().checkPickImagePermission {
            galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun showImage(uri: Uri) {
        imageUri = uri
        binding.apply {
            cvImagePreview.isVisible = true
            ivConfirmImage.isVisible = true
            tvUseThisImage.isVisible = true
            llChooseImageButtons.isVisible = false
            ivChooseImagePreview.setImageURI(imageUri)
        }
    }

    private fun deleteImage() {
        imageUri = null
        binding.apply {
            cvImagePreview.isVisible = false
            ivConfirmImage.isVisible = false
            tvUseThisImage.isVisible = false
            llChooseImageButtons.isVisible = true
        }
    }

    private fun imageError() =
        Toast.makeText(requireContext(), getString(R.string.image_error), Toast.LENGTH_SHORT).show()
}