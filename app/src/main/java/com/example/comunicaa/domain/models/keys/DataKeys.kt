package com.example.comunicaa.domain.models.keys

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataKeys(
    val userId: String = "",
    val categoryId: String = "",
    val subcategoryId: String = "",
    val actionId: String = "",
) : Parcelable
