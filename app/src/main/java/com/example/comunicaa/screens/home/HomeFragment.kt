package com.example.comunicaa.screens.home

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentHomeBinding
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.screens.home.adapter.HomeCategoriesAdapter
import com.example.comunicaa.screens.host.HostViewModel
import com.example.comunicaa.utils.navigate
import com.example.comunicaa.utils.onBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
    override val viewModel: HomeViewModel by viewModels()
    private val hostViewModel: HostViewModel by activityViewModels()

    private val adapter = HomeCategoriesAdapter()

    override fun initViews() {
        setupBackAction()
        setupDrawer()

        viewModel.fetchCategories()
    }

    override fun initObservers() {
        viewModel.categories.observe(viewLifecycleOwner) { categories ->
            setupAdapter(categories)
        }
    }

    private fun setupBackAction() {
        onBackPressed(
            action = { requireActivity().moveTaskToBack(true) },
            closeDrawer = { hostViewModel.hideDrawer() },
            drawerIsOpen = { hostViewModel.drawerIsOpen.value ?: false }
        )
    }

    private fun setupDrawer() {
        binding.ivDrawerToolbar.setOnClickListener { hostViewModel.showDrawer() }
    }

    private fun setupAdapter(categories: List<Category>) {
        adapter.onSubcategorySelected = {
            val direction = HomeFragmentDirections.actionHomeFragmentToActionListFragment()
            navigate(direction)
        }

        binding.rvHome.adapter = adapter
        adapter.submitList(categories)
    }
}