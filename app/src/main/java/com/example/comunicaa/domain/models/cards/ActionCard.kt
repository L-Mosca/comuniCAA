package com.example.comunicaa.domain.models.cards

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ActionCard(
    val id: String? = "",
    val userId: String? = "",
    val categoryId: String? = "",
    val subCategoryId: String? = "",
    val name: String? = "",
    val image: String? = "",
    val sound: String? = "",
    val isDefault: Boolean? = true,
) : Parcelable {
    companion object {
        fun getMockData(): List<ActionCard> {
            val list = mutableListOf<ActionCard>()

            for (i in 1..12) {
                val action = ActionCard(
                    id = "asd",
                    userId = "asd",
                    categoryId = "asd",
                    subCategoryId = "asd",
                    name = "Nome da categoria $i",
                    image = "",
                    sound = "",
                    isDefault = true,
                )
                list.add(action)
            }

            return list
        }
    }
}
