package com.example.comunicaa.screens.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterCategoriesBinding
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.models.cards.SubCategory

class HomeCategoriesAdapter :
    BaseListAdapter<AdapterCategoriesBinding, Category>(CategoryDiffCallback()) {

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