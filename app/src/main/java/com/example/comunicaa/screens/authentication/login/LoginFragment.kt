package com.example.comunicaa.screens.authentication.login

import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentLoginBinding
import com.example.comunicaa.screens.host.HostViewModel
import com.example.comunicaa.utils.hideKeyboard
import com.example.comunicaa.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentLoginBinding =
        FragmentLoginBinding::inflate
    override val viewModel: LoginViewModel by viewModels()
    private val mainViewModel: HostViewModel by activityViewModels()

    override fun initViews() {
        startAnimation()
        setupBackNavigation()
        setupTextFields()
        setupRegisterNavigation()
        setupButtons()
        binding.includeLoginLoading.rlLoading.setOnClickListener {  }
    }

    override fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { showLoading ->
            binding.includeLoginLoading.showLoading(showLoading)
        }

        viewModel.loginSuccess.observe(viewLifecycleOwner) {
            mainViewModel.getUserData()
            mainViewModel.showHome()
        }

        viewModel.loginError.observe(viewLifecycleOwner) {
            showShortSnackBar(getString(R.string.login_error))
        }

        viewModel.emailEmptyError.observe(viewLifecycleOwner) {
            binding.includeLoginFields.tilLoginEmail.error = getString(R.string.email_error)
        }

        viewModel.passwordEmptyError.observe(viewLifecycleOwner) {
            binding.includeLoginFields.tilLoginPassword.error = getString(R.string.password_error)
        }
    }

    private fun setupBackNavigation() {
        binding.vLoginBack.setOnClickListener { findNavController().popBackStack() }
    }

    private fun setupTextFields() {
        binding.includeLoginFields.apply {
            etLoginEmail.addTextChangedListener {
                tilLoginEmail.error = null
            }
            etLoginPassword.addTextChangedListener {
                tilLoginPassword.error = null
            }
            etLoginPassword.setOnEditorActionListener { _, actionId, _ ->
                viewModel.validateKeyboardAction(actionId) { doLogin() }
            }
        }
    }

    private fun setupRegisterNavigation() {
        binding.includeLoginFields.tvDontHasAccount.setOnClickListener {
            val direction = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            navigate(direction)
        }
    }

    private fun setupButtons() {
        binding.includeLoginFields.btLogin.setOnClickListener { doLogin() }
    }

    private fun doLogin() {
        binding.includeLoginFields.apply {
            hideKeyboard()
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()
            viewModel.login(email, password)
        }
    }

    private fun startAnimation() {
        binding.ivLoginBack.visibility = View.INVISIBLE
        binding.tvLoginTitle.visibility = View.INVISIBLE
        binding.includeLoginFields.cvLogin.visibility = View.INVISIBLE

        binding.ivLoginBack.translationY = -1000f
        binding.tvLoginTitle.translationY = -1000f
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