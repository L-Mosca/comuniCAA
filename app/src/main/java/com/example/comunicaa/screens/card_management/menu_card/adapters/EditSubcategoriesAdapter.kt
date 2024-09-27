package com.example.comunicaa.screens.card_management.menu_card.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterEditSubcategoriesBinding
import com.example.comunicaa.domain.models.cards.SubCategory

class EditSubcategoriesAdapter :
    BaseListAdapter<AdapterEditSubcategoriesBinding, SubCategory>(EditSubcategoryDiffUtil()) {

    class EditSubcategoryDiffUtil : DiffUtil.ItemCallback<SubCategory>() {
        override fun areItemsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem == newItem
    }

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterEditSubcategoriesBinding
        get() = { layoutInflater, viewGroup ->
            AdapterEditSubcategoriesBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        }

    var onSubcategoryClicked: ((View, SubCategory) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterEditSubcategoriesBinding>,
        data: SubCategory,
        position: Int
    ) {
        holder.binding.apply {
            cvEditSubcategories.setOnClickListener { onSubcategoryClicked?.invoke(it, data) }
            tvEditSubcategoryName.text = data.name
        }
    }
}