package com.example.comunicaa.screens.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterUserCardBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.squareup.picasso.Picasso

class HomeUserCardsAdapter :
    BaseListAdapter<AdapterUserCardBinding, ActionCard>(HomeUserCardsDiffUtil()) {

    class HomeUserCardsDiffUtil : DiffUtil.ItemCallback<ActionCard>() {
        override fun areItemsTheSame(oldItem: ActionCard, newItem: ActionCard): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ActionCard, newItem: ActionCard): Boolean {
            return oldItem == newItem
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterUserCardBinding
        get() = { layoutInflater, viewGroup ->
            AdapterUserCardBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        }

    var onCardSelected: ((ActionCard) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterUserCardBinding>,
        data: ActionCard,
        position: Int
    ) {
        holder.binding.apply {
            cvSubcategories.setOnClickListener { onCardSelected?.invoke(data) }
            tvSubcategoryName.text = data.name

            data.image?.let {
                with(includeSubcategoryImage) {
                    Picasso.get()
                        .load(it)
                        .into(ivSubcategory, object : com.squareup.picasso.Callback {
                            override fun onSuccess() {
                                piActionLoading.visibility = View.GONE
                            }

                            override fun onError(e: Exception?) {
                                piActionLoading.visibility = View.GONE
                            }
                        })
                }
            }
        }
    }
}