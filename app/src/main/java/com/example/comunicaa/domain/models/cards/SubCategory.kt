package com.example.comunicaa.domain.models.cards

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SubCategory(
    val id: String? = "",
    val userId: String? = "",
    val categoryId: String? = "",
    val name: String? = "",
    val image: String? = "",
    val color: Int? = -1,
    val actions: List<ActionCard>? = emptyList(),
    val isDefault: Boolean? = true,
) : Parcelable {
    companion object {
        fun getMockData(): List<SubCategory> {
            val list = mutableListOf<SubCategory>()

            for (i in 1..5) {
                val subcategory = SubCategory(
                    id = "$i",
                    userId = "dsadsa",
                    categoryId = "dsadas",
                    name = "Nome da subcategoria $i",
                    image = "",
                    color = 0,
                    actions = ActionCard.getMockData(),
                    isDefault = false,
                )
                list.add(subcategory)
            }

            return list
        }
    }
}
