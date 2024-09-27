package com.example.comunicaa.screens.card_management.menu_card

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentMenuCardBinding
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.screens.card_management.menu_card.adapters.EditCategoriesAdapter
import com.example.comunicaa.utils.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuCardFragment : BaseFragment<FragmentMenuCardBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentMenuCardBinding =
        FragmentMenuCardBinding::inflate
    override val viewModel: MenuCardViewModel by viewModels()

    private val adapter = EditCategoriesAdapter()

    override fun initViews() {
        setupHeader()
        viewModel.fetchData()
    }

    override fun initObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { setupAdapter(it) }
    }

    private fun setupHeader() {
        binding.includeMenuCardHeader.apply {
            tvHeaderTitle.text = getString(R.string.my_cards)
            ivCreateCardHeaderBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setupAdapter(data: List<Category>) {
        adapter.submitList(data)

        adapter.onEditCategoryClicked = {
            val direction = MenuCardFragmentDirections.actionMenuCardFragmentToCreateCardFragment(null)
            navigate(direction)
        }
        adapter.onSubcategoryClicked = { view, _ ->
            showPopupMenu(view, R.menu.drawer_menu) {
                it.setOnMenuItemClickListener {
                    // todo handle menu item click
                    true
                }
            }
        }

        binding.rvEditCategories.adapter = adapter
    }
}