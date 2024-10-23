package com.example.comunicaa.screens.action_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.comunicaa.base.BaseListAdapter
import com.example.comunicaa.base.ViewHolder
import com.example.comunicaa.databinding.AdapterActionListBinding
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.utils.toDpMetric
import com.squareup.picasso.Picasso
import java.lang.Exception

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

        if (viewType == LAST_VIEW) {
            viewBinding.root.updateLayoutParams<RecyclerView.LayoutParams> {
                bottomMargin = 40.toDpMetric(parent)
            }
        }

        return ViewHolder(viewBinding)
    }

    override fun getItemViewType(position: Int) = when (position) {
        0 -> FIRST_VIEW
        currentList.lastIndex -> LAST_VIEW
        else -> DEFAULT_VIEW
    }

    var onCardPressed: ((ActionCard) -> Unit)? = null

    override fun onBindViewHolder(
        holder: ViewHolder<AdapterActionListBinding>,
        data: ActionCard,
        position: Int
    ) {
        holder.binding.apply {

            cvActionItem.setOnClickListener { onCardPressed?.invoke(data) }
            tvActionName.text = data.name

            includeActionImage.ivSubcategory.apply {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                layoutParams.height = (200 * context.resources.displayMetrics.density).toInt()
                scaleType = ImageView.ScaleType.CENTER_INSIDE
            }

            Picasso.get()
                .load(data.image)
                .into(includeActionImage.ivSubcategory, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        includeActionImage.piActionLoading.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        includeActionImage.piActionLoading.visibility = View.GONE
                    }

                })
        }
    }
}