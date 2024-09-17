package com.example.comunicaa.screens.card_management.menu_card

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentMenuCardBinding
import com.example.comunicaa.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuCardFragment : BaseFragment<FragmentMenuCardBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentMenuCardBinding =
        FragmentMenuCardBinding::inflate
    override val viewModel: MenuCardViewModel by viewModels()

    override fun initViews() {
        binding.apply {
            btCreateCard.setOnClickListener {
                val direction = MenuCardFragmentDirections.actionMenuCardFragmentToCreateCardFragment()
                navigate(direction)
            }
            btEditCard.setOnClickListener {
                val direction = MenuCardFragmentDirections.actionMenuCardFragmentToEditCardFragment()
                navigate(direction)
            }
        }
    }

    override fun initObservers() {}
}