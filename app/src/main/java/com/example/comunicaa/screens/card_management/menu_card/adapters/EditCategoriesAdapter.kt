package com.example.comunicaa.screens.card_management.menu_card.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterUserCardBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.utils.toDpMetric
import com.squareup.picasso.Picasso

class EditCategoriesAdapter :
    BaseListAdapter<AdapterUserCardBinding, ActionCard>(EditCategoryDiffUtil()) {

    companion object {
        const val FIRST_VIEW = 0
        const val DEFAULT_VIEW = 1
        const val LAST_VIEW = 2
    }

    class EditCategoryDiffUtil : DiffUtil.ItemCallback<ActionCard>() {
        override fun areItemsTheSame(oldItem: ActionCard, newItem: ActionCard) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ActionCard, newItem: ActionCard) =
            oldItem == newItem
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterUserCardBinding> {
        val inflater = LayoutInflater.from(parent.context)
        val viewBinding = bindingInflater(inflater, parent)

        viewBinding.root.updateLayoutParams<RecyclerView.LayoutParams> {
            topMargin = 20.toDpMetric(parent)
        }

        return ViewHolder(viewBinding)
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> FIRST_VIEW
        currentList.lastIndex -> LAST_VIEW
        else -> DEFAULT_VIEW
    }

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterUserCardBinding
        get() = { layoutInflater, viewGroup ->
            AdapterUserCardBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        }

    var onEditCategoryClicked: ((ActionCard) -> Unit)? = null
    var onCardSelected: ((View, ActionCard) -> Unit)? = null

    var isEnabled = true

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterUserCardBinding>,
        data: ActionCard,
        position: Int
    ) {
        holder.binding.apply {
            cvSubcategories.setOnClickListener { onCardSelected?.invoke(it, data) }
            cvSubcategories.isEnabled = isEnabled

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