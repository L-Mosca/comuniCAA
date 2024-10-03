package com.example.comunicaa.domain.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginBody(
    val email: String,
    val password: String,
) : Parcelable

fun buildLoginBody(email: String, password: String) = LoginBody(email, password)