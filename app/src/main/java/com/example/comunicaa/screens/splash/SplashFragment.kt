package com.example.comunicaa.screens.splash

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentSplashBinding =
        FragmentSplashBinding::inflate
    override val viewModel: SplashViewModel by viewModels()

    override fun initViews() {}

    override fun initObservers() {}
}