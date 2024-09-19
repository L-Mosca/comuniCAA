package com.example.comunicaa.domain.models.cards

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: String? = "",
    val userId: String? = "",
    val name: String? = "",
    val subCategories: List<SubCategory>? = emptyList(),
    val isDefault: Boolean? = true,
) : Parcelable {
    companion object {
        fun getMockData(): List<Category> {
            val list = mutableListOf<Category>()

            for (i in 1..5) {
                val category =
                    Category(
                        id = "$i",
                        userId = "dsadsa",
                        name = "Nome da categoria $i",
                        subCategories = SubCategory.getMockData(),
                        isDefault = true,
                    )
                list.add(category)
            }

            return list
        }
    }
}
