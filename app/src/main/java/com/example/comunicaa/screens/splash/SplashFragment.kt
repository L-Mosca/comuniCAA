package com.example.comunicaa.screens.splash

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentSplashBinding =
        FragmentSplashBinding::inflate
    override val viewModel: SplashViewModel by viewModels()

    override fun initViews() {
        viewModel.fetchData()
    }

    override fun initObservers() {
        viewModel.showHomeScreen.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.home_nav_graph)
        }
    }
}