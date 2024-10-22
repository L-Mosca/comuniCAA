@file:Suppress("DEPRECATION")

package com.example.comunicaa.screens.card_management.create_card.bottom_sheets

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseBottomSheet
import com.example.comunicaa.databinding.BottomSheetSelectSubcategoryBinding
import com.example.comunicaa.domain.models.cards.SubCategory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectSubcategoryBottomSheet : BaseBottomSheet<BottomSheetSelectSubcategoryBinding>() {
    override val bindingInflater: (LayoutInflater) -> BottomSheetSelectSubcategoryBinding =
        BottomSheetSelectSubcategoryBinding::inflate
    override val viewModel: SelectSubcategoryViewModel by viewModels()

    private val adapter = SelectSubcategoryAdapter()
    private var subcategoty: SubCategory? = null

    var onSubcategorySelect: ((SubCategory) -> Unit)? = null

    companion object {
        private const val SELECT_SUBCATEGORY_EXTRA = "SelectSubcategoryExtra.subcategorySelected"

        fun newInstance(data: SubCategory?): SelectSubcategoryBottomSheet {
            return SelectSubcategoryBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECT_SUBCATEGORY_EXTRA, data)
                }
            }
        }
    }

    override fun getBundleArguments() {
        arguments?.getParcelable<SubCategory>(SELECT_SUBCATEGORY_EXTRA)?.let { subcategoty = it }
    }

    override fun initViews() {
        viewModel.fetchSubcategories()
    }

    override fun initObservers() {
        viewModel.subcategories.observe(viewLifecycleOwner) { setupAdapter(it) }
    }

    private fun setupAdapter(data: List<SubCategory>) {
        adapter.onSubcategorySelected = { onSubcategorySelect?.invoke(it) }
        adapter.submitList(data)
        binding.rvSelectSubcategory.adapter = adapter
    }
}