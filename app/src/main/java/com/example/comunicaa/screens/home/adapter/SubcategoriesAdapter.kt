package com.example.comunicaa.screens.home.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterSubcategoriesBinding
import com.example.comunicaa.domain.models.cards.SubCategory
import com.squareup.picasso.Picasso

class SubcategoriesAdapter :
    BaseListAdapter<AdapterSubcategoriesBinding, SubCategory>(SubcategoryDiffUtil()) {

    class SubcategoryDiffUtil : DiffUtil.ItemCallback<SubCategory>() {
        override fun areItemsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: SubCategory, newItem: SubCategory) =
            oldItem == newItem
    }

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterSubcategoriesBinding
        get() = { layoutInflater, viewGroup ->
            AdapterSubcategoriesBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        }

    var onItemClick: ((SubCategory) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterSubcategoriesBinding>,
        data: SubCategory,
        position: Int
    ) {
        holder.binding.apply {
            tvSubcategoryName.text = data.name
            cvSubcategories.setOnClickListener { onItemClick?.invoke(data) }
            cvSubcategories.backgroundTintList = ColorStateList.valueOf(data.color ?: Color.WHITE)
            if (!data.image.isNullOrEmpty()) {
                Picasso.get().load(data.image).into(includeSubcategoryImage.ivSubcategory)
            }
        }
    }
}