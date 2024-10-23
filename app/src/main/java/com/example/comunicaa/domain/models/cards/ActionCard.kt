package com.example.comunicaa.domain.models.cards

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import java.text.Collator
import java.util.Locale

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

        @Exclude
        fun convertToCardList(data: Any?) : List<ActionCard> {
            val list = mutableListOf<ActionCard>()
            if (data is Map<*, *>) {
                data.forEach { (_, value) ->
                    if (value is Map<*, *>) {
                        val id = value["id"] as String
                        val userId = value["userId"] as String
                        val categoryId = value["categoryId"] as String
                        val subCategoryId = value["subCategoryId"] as String
                        val name = value["name"] as String
                        val image = value["image"] as String
                        val sound = value["sound"] as String
                        val isDefault = value["isDefault"] as Boolean
                        val card = ActionCard(id, userId, categoryId, subCategoryId, name, image, sound, isDefault)
                        list.add(card)
                    }
                }
            }
            val collator = Collator.getInstance(Locale("pt", "BR"))
            collator.strength = Collator.PRIMARY

            return list.sortedWith(compareBy(collator) { it.name })
        }
    }
}
