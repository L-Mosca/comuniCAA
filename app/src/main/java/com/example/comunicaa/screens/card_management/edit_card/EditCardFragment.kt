package com.example.comunicaa.screens.card_management.edit_card

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentEditCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCardFragment : BaseFragment<FragmentEditCardBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentEditCardBinding =
        FragmentEditCardBinding::inflate
    override val viewModel: EditCardViewModel by viewModels()

    override fun initViews() {}

    override fun initObservers() {}
}