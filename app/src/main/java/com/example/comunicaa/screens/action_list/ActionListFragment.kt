package com.example.comunicaa.screens.action_list

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentActionListBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.screens.host.HostViewModel
import com.example.comunicaa.utils.onBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActionListFragment : BaseFragment<FragmentActionListBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentActionListBinding =
        FragmentActionListBinding::inflate
    override val viewModel: ActionListViewModel by viewModels()
    private val hostViewModel: HostViewModel by activityViewModels()

    private val adapter = ActionListAdapter()

    override fun initViews() {
        setupBackAction()
        binding.ivActionDrawerToolbar.setOnClickListener { findNavController().popBackStack() }
        viewModel.fetchActions()
    }

    override fun initObservers() {
        viewModel.actionList.observe(viewLifecycleOwner) { actionList ->
            setupAdapter(actionList)
        }
    }

    private fun setupBackAction() {
        onBackPressed(
            action = { findNavController().popBackStack() },
            closeDrawer = { hostViewModel.hideDrawer() },
            drawerIsOpen = { hostViewModel.drawerIsOpen.value ?: false }
        )
    }

    private fun setupAdapter(list: List<ActionCard>) {
        adapter.submitList(list)
        binding.rvAction.adapter = adapter
    }
}