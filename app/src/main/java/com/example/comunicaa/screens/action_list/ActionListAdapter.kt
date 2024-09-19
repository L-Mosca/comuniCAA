package com.example.comunicaa.screens.action_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterActionListBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.utils.toDpMetric

class ActionListAdapter : BaseListAdapter<AdapterActionListBinding, ActionCard>(ActionDiffUtil()) {

    companion object {
        const val FIRST_VIEW = 0
        const val DEFAULT_VIEW = 1
        const val LAST_VIEW = 2
    }

    class ActionDiffUtil : DiffUtil.ItemCallback<ActionCard>() {
        override fun areItemsTheSame(oldItem: ActionCard, newItem: ActionCard) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ActionCard, newItem: ActionCard) =
            oldItem == newItem
    }

    override val bindingInflater: (LayoutInflater, ViewGroup) -> AdapterActionListBinding
        get() = { layoutInflater, viewGroup ->
            AdapterActionListBinding.inflate(
                layoutInflater,
                viewGroup,
                false
            )
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<AdapterActionListBinding> {
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

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterActionListBinding>,
        data: ActionCard,
        position: Int
    ) {
        holder.binding.apply {
            tvActionName.text = data.name
        }
    }
}