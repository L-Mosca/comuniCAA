package com.example.comunicaa.screens.action_list

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentActionListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActionListFragment : BaseFragment<FragmentActionListBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentActionListBinding =
        FragmentActionListBinding::inflate
    override val viewModel: ActionListViewModel by viewModels()

    override fun initViews() {}

    override fun initObservers() {}
}