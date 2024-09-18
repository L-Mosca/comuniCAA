package com.example.comunicaa.screens.authentication.register

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    override val viewModel: RegisterViewModel by viewModels()

    override fun initViews() {
        startAnimation()
        binding.vRegisterBack.setOnClickListener { findNavController().popBackStack() }
        binding.includeRegisterFields.tvHasAccount.setOnClickListener { findNavController().popBackStack() }
    }

    override fun initObservers() {}

    private fun startAnimation() {
        binding.ivRegisterBack.visibility = View.INVISIBLE
        binding.tvRegisterTitle.visibility = View.INVISIBLE
        binding.includeRegisterFields.cvRegister.visibility = View.INVISIBLE

        binding.ivRegisterBack.translationY = -1000f
        binding.tvRegisterTitle.translationY = -1000f
        binding.includeRegisterFields.cvRegister.translationY = 1000f
        binding.vRegisterBackground.translationY = -1000f

        binding.ivRegisterBack.animate()
            .translationY(0f)
            .setDuration(450)
            .withStartAction { binding.ivRegisterBack.visibility = View.VISIBLE }
            .start()

        binding.tvRegisterTitle.animate()
            .translationY(0f)
            .setDuration(450)
            .setStartDelay(100)
            .withStartAction { binding.tvRegisterTitle.visibility = View.VISIBLE }
            .start()

        binding.includeRegisterFields.cvRegister.animate()
            .translationY(0f)
            .setDuration(450)
            .setStartDelay(100)
            .withStartAction { binding.includeRegisterFields.cvRegister.visibility = View.VISIBLE }
            .start()

        binding.vRegisterBackground.animate()
            .translationY(0f)
            .setDuration(450)
            .withStartAction { binding.vRegisterBackground.visibility = View.VISIBLE }
            .start()
    }
}