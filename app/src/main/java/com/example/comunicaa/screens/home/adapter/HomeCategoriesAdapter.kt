package com.example.comunicaa.screens.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterCategoriesBinding
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.models.cards.SubCategory
import com.example.comunicaa.utils.toDpMetric

class HomeCategoriesAdapter :
    BaseListAdapter<AdapterCategoriesBinding, Category>(CategoryDiffCallback()) {

    companion object {
        const val FIRST_VIEW = 0
        const val DEFAULT_VIEW = 1
        const val LAST_VIEW = 2
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterCategoriesBinding
        get() = { layoutInflater, viewGroup ->
            AdapterCategoriesBinding.inflate(
                layoutInflater, viewGroup, false
            )
        }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> FIRST_VIEW
        currentList.lastIndex -> LAST_VIEW
        else -> DEFAULT_VIEW
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterCategoriesBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = bindingInflater(inflater, parent)

        if (viewType == FIRST_VIEW) {
            viewBinding.root.updateLayoutParams<RecyclerView.LayoutParams> {
                topMargin = 40.toDpMetric(parent)
            }
        }

        if (viewType == LAST_VIEW) {
            viewBinding.root.updateLayoutParams<RecyclerView.LayoutParams> {
                bottomMargin = 20.toDpMetric(parent)
            }
        }

        return ViewHolder(viewBinding)
    }

    var onSubcategorySelected: ((SubCategory) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterCategoriesBinding>, data: Category, position: Int
    ) {
        holder.binding.apply {
            tvCategoryName.text = data.name

            val subcategoriesAdapter = SubcategoriesAdapter()
            subcategoriesAdapter.submitList(data.subCategories)
            rvCategories.layoutManager = GridLayoutManager(root.context, 2)
            rvCategories.adapter = subcategoriesAdapter

            subcategoriesAdapter.onItemClick = { onSubcategorySelected?.invoke(it) }
        }
    }
}