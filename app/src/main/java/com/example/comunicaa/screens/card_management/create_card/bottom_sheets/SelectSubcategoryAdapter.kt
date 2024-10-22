package com.example.comunicaa.screens.card_management.create_card.bottom_sheets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterSelectSubcategoryBinding
import com.example.comunicaa.domain.models.cards.SubCategory

class SelectSubcategoryAdapter :
    BaseListAdapter<AdapterSelectSubcategoryBinding, SubCategory>(SelectSubcategoryDiffUtil()) {

    class SelectSubcategoryDiffUtil : DiffUtil.ItemCallback<SubCategory>() {
        override fun areItemsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem == newItem
    }

    var onSubcategorySelected: ((SubCategory) -> Unit)? = null

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterSelectSubcategoryBinding
        get() = { layoutInflater, viewGroup ->
            AdapterSelectSubcategoryBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        }

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterSelectSubcategoryBinding>,
        data: SubCategory,
        position: Int
    ) {
        holder.binding.apply {
            root.setOnClickListener { onSubcategorySelected?.invoke(data) }
            tvSelectSubcategoryName.text = data.name
        }
    }
}