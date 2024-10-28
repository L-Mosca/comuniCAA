package com.example.comunicaa.screens.card_management.menu_card

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentMenuCardBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.keys.DataKeys
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
    }

    override fun initObservers() {
        viewModel.userCards.observe(viewLifecycleOwner) { setupAdapter(it) }

        viewModel.deleteCardError.observe(viewLifecycleOwner) {
            showShortSnackBar(getString(R.string.delete_card_error))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    private fun setupHeader() {
        binding.includeMenuCardHeader.apply {
            tvHeaderTitle.text = getString(R.string.my_cards)
            ivCreateCardHeaderBack.setOnClickListener { findNavController().popBackStack() }
        }
    }

    private fun setupAdapter(data: List<ActionCard>) {
        binding.rvEditCategories.adapter = adapter
        adapter.submitList(data)

        adapter.onEditCategoryClicked = {
            val direction =
                MenuCardFragmentDirections.actionMenuCardFragmentToCreateCardFragment(null)
            navigate(direction)
        }

        adapter.onCardSelected = { view, card ->
            showPopupMenu(view, R.menu.user_cards_menu) { menuItem ->
                menuItem.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.menuEditCard -> goToEditAction(card)
                        R.id.menuDeleteCard -> viewModel.deleteCard(card.userId, card.id)
                    }
                    true
                }
            }
        }
    }

    private fun goToEditAction(action: ActionCard) {
        val keys = DataKeys.build(action)
        val direction = MenuCardFragmentDirections.actionMenuCardFragmentToCreateCardFragment(keys)
        navigate(direction)
    }
}