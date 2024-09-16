package com.example.comunicaa.screens.authentication.register

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    override val viewModel: RegisterViewModel by viewModels()

    override fun initViews() {}

    override fun initObservers() {}
}