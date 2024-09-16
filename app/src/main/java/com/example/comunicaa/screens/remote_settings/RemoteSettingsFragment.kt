package com.example.comunicaa.screens.remote_settings

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentRemoteSettingsBinding

class RemoteSettingsFragment : BaseFragment<FragmentRemoteSettingsBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentRemoteSettingsBinding =
        FragmentRemoteSettingsBinding::inflate
    override val viewModel: RemoteSettingsViewModel by viewModels()

    override fun initViews() {}

    override fun initObservers() {}
}