package com.example.comunicaa.screens.authentication.register

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentRegisterBinding
import com.example.comunicaa.screens.host.HostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentRegisterBinding =
        FragmentRegisterBinding::inflate
    override val viewModel: RegisterViewModel by viewModels()
    val mainViewModel: HostViewModel by activityViewModels()

    override fun initViews() {
        binding.includeRegisterFields.tvHasAccount.setOnClickListener { findNavController().popBackStack() }
        startAnimation()
        setupTextFields()
        setupRegisterButton()
        setupHeader()
    }

    override fun initObservers() {
        viewModel.loginSuccess.observe(viewLifecycleOwner) {

        }

        viewModel.loginError.observe(viewLifecycleOwner) {}

        viewModel.confirmPasswordError.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tilRegisterConfirmPassword.error =
                getString(R.string.password_equals_error)
        }

        viewModel.usernameError.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tilRegisterName.error = getString(R.string.username_error)
        }

        viewModel.showEmptyEmail.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tilRegisterEmail.error = getString(R.string.email_error)
        }

        viewModel.passwordLength.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tvMinimumCharacters.setTextColor(
                ContextCompat.getColor(requireContext(), it)
            )
        }

        viewModel.passwordUpperLetter.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tvUpperLetter.setTextColor(
                ContextCompat.getColor(requireContext(), it)
            )
        }

        viewModel.passwordLowerLetter.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tvLowerLetter.setTextColor(
                ContextCompat.getColor(requireContext(), it)
            )
        }

        viewModel.passwordNumber.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tvNumber.setTextColor(
                ContextCompat.getColor(requireContext(), it)
            )
        }

        viewModel.passwordEmpty.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tilRegisterPassword.error =
                getString(R.string.password_error)
        }

        viewModel.confirmPasswordEmpty.observe(viewLifecycleOwner) {
            binding.includeRegisterFields.tilRegisterConfirmPassword.error =
                getString(R.string.password_error)
        }

    }

    private fun setupTextFields() {
        binding.includeRegisterFields.apply {
            etRegisterName.addTextChangedListener { tilRegisterName.error = null }

            etRegisterEmail.addTextChangedListener { tilRegisterEmail.error = null }

            etRegisterPassword.addTextChangedListener {
                tilRegisterPassword.error = null
                viewModel.validatePasswordRequirements(it.toString())
            }

            etRegisterConfirmPassword.addTextChangedListener {
                tilRegisterConfirmPassword.error = null
            }
        }
    }

    private fun setupRegisterButton() {
        binding.includeRegisterFields.apply {
            btRegister.setOnClickListener {
                val username = etRegisterName.text.toString()
                val email = etRegisterEmail.text.toString()
                val password = etRegisterPassword.text.toString()
                val confirmPassword = etRegisterConfirmPassword.text.toString()

                viewModel.login(username, email, password, confirmPassword)
            }
        }
    }

    private fun setupHeader() {
        binding.vRegisterBack.setOnClickListener { findNavController().popBackStack() }
    }

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