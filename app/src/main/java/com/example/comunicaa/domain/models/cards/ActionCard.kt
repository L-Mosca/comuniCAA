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
) : Parcelable