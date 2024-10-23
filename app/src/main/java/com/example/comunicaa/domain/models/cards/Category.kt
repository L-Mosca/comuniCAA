package com.example.comunicaa.domain.models.cards

import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.Exclude
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
            return list
        }
    }
}
