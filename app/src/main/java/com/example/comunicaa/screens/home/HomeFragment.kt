package com.example.comunicaa.screens.home

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentHomeBinding
import com.example.comunicaa.utils.onBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
    override val viewModel: HomeViewModel by viewModels()

    override fun initViews() {
        onBackPressed { requireActivity().moveTaskToBack(true) }
    }

    override fun initObservers() {}
}