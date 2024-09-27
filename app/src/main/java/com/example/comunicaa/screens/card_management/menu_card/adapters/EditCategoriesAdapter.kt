package com.example.comunicaa.screens.card_management.menu_card.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterEditCategoriesBinding
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.models.cards.SubCategory
import com.example.comunicaa.utils.toDpMetric

class EditCategoriesAdapter :
    BaseListAdapter<AdapterEditCategoriesBinding, Category>(EditCategoryDiffUtil()) {

    companion object {
        const val FIRST_VIEW = 0
        const val DEFAULT_VIEW = 1
        const val LAST_VIEW = 2
    }

    class EditCategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterEditCategoriesBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = bindingInflater(inflater, parent)

        if (viewType == FIRST_VIEW) {
            viewBinding.root.updateLayoutParams<RecyclerView.LayoutParams> {
                topMargin = 40.toDpMetric(parent)
            }
        }

        return ViewHolder(viewBinding)
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> FIRST_VIEW
        currentList.lastIndex -> LAST_VIEW
        else -> DEFAULT_VIEW
    }

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterEditCategoriesBinding
        get() = { layoutInflater, viewGroup ->
            AdapterEditCategoriesBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        }

    var onEditCategoryClicked: ((Category) -> Unit)? = null
    var onSubcategoryClicked: ((View, SubCategory) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterEditCategoriesBinding>,
        data: Category,
        position: Int
    ) {
        holder.binding.apply {
            ivEditCategory.setOnClickListener { onEditCategoryClicked?.invoke(data) }
            tvEditCategoryName.text = data.name

            val adapter = EditSubcategoriesAdapter()
            adapter.submitList(data.subCategories)
            rvEditCategories.adapter = adapter

            adapter.onSubcategoryClicked = { view, data ->
                onSubcategoryClicked?.invoke(view, data)
            }
        }
    }
}