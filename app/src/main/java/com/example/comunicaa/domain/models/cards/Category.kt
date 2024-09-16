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
): Parcelable
