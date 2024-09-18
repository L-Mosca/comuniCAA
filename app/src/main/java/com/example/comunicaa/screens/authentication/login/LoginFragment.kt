package com.example.comunicaa.screens.authentication.login

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentLoginBinding
import com.example.comunicaa.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate
    override val viewModel: LoginViewModel by viewModels()

    override fun initViews() {
        startAnimation()

        binding.vLoginBack.setOnClickListener { findNavController().popBackStack() }
        binding.includeLoginFields.tvDontHasAccount.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navigate(direction)
        }
    }

    override fun initObservers() {}


    private fun startAnimation() {
        binding.ivLoginBack.visibility = View.INVISIBLE
        binding.tvLoginTitle.visibility = View.INVISIBLE
        binding.includeLoginFields.cvLogin.visibility = View.INVISIBLE

        binding.ivLoginBack.translationY = 1000f
        binding.tvLoginTitle.translationY = 1000f
        binding.includeLoginFields.cvLogin.translationY = 1000f
        binding.vLoginBackground.translationY = -1000f

        binding.ivLoginBack.animate()
            .translationY(0f)
            .setDuration(450)
            .withStartAction { binding.ivLoginBack.visibility = View.VISIBLE }
            .start()

        binding.tvLoginTitle.animate()
            .translationY(0f)
            .setDuration(450)
            .setStartDelay(100)
            .withStartAction { binding.tvLoginTitle.visibility = View.VISIBLE }
            .start()

        binding.includeLoginFields.cvLogin.animate()
            .translationY(0f)
            .setDuration(450)
            .setStartDelay(100)
            .withStartAction { binding.includeLoginFields.cvLogin.visibility = View.VISIBLE }
            .start()

        binding.vLoginBackground.animate()
            .translationY(0f)
            .setDuration(450)
            .withStartAction { binding.vLoginBackground.visibility = View.VISIBLE }
            .start()
    }
}