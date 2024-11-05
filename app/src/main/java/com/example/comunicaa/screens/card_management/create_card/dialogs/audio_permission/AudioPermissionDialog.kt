package com.example.comunicaa.screens.card_management.create_card.dialogs.audio_permission

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseDialog
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.databinding.DialogAudioPermissionBinding

class AudioPermissionDialog : BaseDialog<DialogAudioPermissionBinding>() {

    override val bindingInflater: (LayoutInflater) -> DialogAudioPermissionBinding =
        DialogAudioPermissionBinding::inflate
    override val viewModel: BaseViewModel by viewModels()

    companion object {
        fun newInstance(): AudioPermissionDialog = AudioPermissionDialog()
    }

    var onSettingsPressed: (() -> Unit)? = null

    override fun initViews() {
        binding.apply {
            btAccessPermissions.setOnClickListener { onSettingsPressed?.invoke() }
        }
    }

    override fun initObservers() {}

    override fun getBundleArguments() {}
}