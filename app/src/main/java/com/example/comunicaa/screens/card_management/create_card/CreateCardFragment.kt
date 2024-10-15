package com.example.comunicaa.screens.card_management.create_card

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentCreateCardBinding
import com.example.comunicaa.screens.card_management.create_card.dialogs.ChooseImageProviderDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCardFragment : BaseFragment<FragmentCreateCardBinding>() {

    companion object {
        const val REQUEST_PERMISSION = 1357
    }

    var uri: Uri? = null

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
    }

    override fun initObservers() {}

    private fun setupSelectImage() {
        binding.includeCreateActionSelectImage.cvCreateActionSelectImage.setOnClickListener {
            val fragment = ChooseImageProviderDialog.newInstance(uri)
            fragment.onImageSelected = {
                uri = it
                fragment.dismiss()
                binding.includeCreateActionPreview.ivCreatePreview.setImageURI(uri)
            }
            fragment.show(childFragmentManager, "chooseImage")
        }
    }

}