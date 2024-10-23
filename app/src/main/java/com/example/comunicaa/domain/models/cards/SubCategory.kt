package com.example.comunicaa.domain.models.cards

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import java.text.Collator
import java.util.Locale

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

        @Exclude
        fun convertToSubcategoryList(data: Any?) : List<SubCategory> {
            val list = mutableListOf<SubCategory>()
            if (data is Map<*, *>) {
                data.forEach { (_, value) ->
                    if (value is Map<*, *>) {
                        val id = value["id"] as String
                        val userId = value["userId"] as String
                        val categoryId = value["categoryId"] as String
                        val name = value["name"] as String
                        val image = value["image"] as String
                        val color = (value["color"] as Long).toInt()
                        val actions = ActionCard.convertToCardList(value["actions"])
                        val isDefault = value["isDefault"] as Boolean
                        val subcategory = SubCategory(id, userId, categoryId, name, image, color, actions, isDefault)
                        list.add(subcategory)
                    }
                }
            }
            val collator = Collator.getInstance(Locale("pt", "BR"))
            collator.strength = Collator.PRIMARY

            return list.sortedWith(compareBy(collator) { it.name })
        }
    }
}
