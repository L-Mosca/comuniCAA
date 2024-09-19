package com.example.comunicaa.screens.splash

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentSplashBinding
import com.example.comunicaa.utils.AnimationUtils
import com.example.comunicaa.utils.delayed
import com.example.comunicaa.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentSplashBinding =
        FragmentSplashBinding::inflate
    override val viewModel: SplashViewModel by viewModels()

    override fun initViews() {
        viewModel.fetchData()
        runIconAnimation()
    }

    override fun initObservers() {
        viewModel.showHomeScreen.observe(viewLifecycleOwner) {
            val direction = SplashFragmentDirections.actionSplashFragmentToHomeNavGraph()
            delayed(action = { navigate(direction) }, duration = 300L)
        }
    }

    private fun runIconAnimation() {
        delayed(action = { AnimationUtils.scale(binding.ivSplashIcon, requireContext()) })
    }
}