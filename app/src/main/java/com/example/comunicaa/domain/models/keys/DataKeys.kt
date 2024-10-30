package com.example.comunicaa.domain.models.keys

import android.os.Parcelable
import com.example.comunicaa.domain.models.cards.ActionCard
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataKeys(
    val userId: String = "",
    val categoryId: String = "",
    val subcategoryId: String = "",
    val actionId: String = "",
) : Parcelable {
    companion object {
        fun build(card: ActionCard): DataKeys {
            return DataKeys(card.userId!!, card.categoryId!!, card.subCategoryId!!, card.id!!)
        }
    }
}
