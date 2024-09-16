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
): Parcelable
