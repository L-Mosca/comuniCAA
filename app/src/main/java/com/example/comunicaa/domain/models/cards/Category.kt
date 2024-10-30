package com.example.comunicaa.domain.models.cards

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize
import java.text.Collator
import java.util.Locale

@Parcelize
data class Category(
    val id: String? = "",
    val userId: String? = "",
    val name: String? = "",
    val subCategories: List<SubCategory>? = emptyList(),
    val isDefault: Boolean? = true,
) : Parcelable {
    companion object {
        const val DEFAULT_ID = "000001"

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

        @Exclude
        fun convertToCategoryList(data: HashMap<String, Any>?): List<Category> {
            val list = mutableListOf<Category>()
            data?.forEach { (_, value) ->
                if (value is Map<*, *>) {
                    val id = value["id"] as String
                    val userId = value["userId"] as String
                    val name = value["name"] as String
                    val isDefault = value["isDefault"] as Boolean
                    val subCategories = SubCategory.convertToSubcategoryList(value["subCategories"])
                    val category = Category(id, userId, name, subCategories, isDefault)
                    list.add(category)
                }
            }
            val collator = Collator.getInstance(Locale("pt", "BR"))
            collator.strength = Collator.PRIMARY

            return list.sortedWith(compareBy(collator) { it.name })
        }

        fun buildDefaultUserCategory(userId: String): Category {
            return Category(
                id = DEFAULT_ID,
                userId = userId,
                name = "Meus cartões",
                subCategories = emptyList(),
                isDefault = false
            )
        }
    }

    fun toMap() : Map<String, Any?> {
        return mapOf(
            "id" to id,
            "userId" to userId,
            "name" to name,
            "subCategories" to "",
            "isDefault" to isDefault
        )
    }
}
