package com.example.comunicaa.screens.card_management.create_card

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentCreateCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateCardFragment : BaseFragment<FragmentCreateCardBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentCreateCardBinding =
        FragmentCreateCardBinding::inflate
    override val viewModel: CreateCardViewModel by viewModels()

    private val navArgs: CreateCardFragmentArgs by navArgs()

    override fun initViews() {
        binding.includeCreateActionHeader.ivCreateCardHeaderBack.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.fetchCardData(navArgs.keys)
    }

    override fun initObservers() {}
}