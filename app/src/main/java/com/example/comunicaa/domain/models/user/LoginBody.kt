package com.example.comunicaa.domain.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginBody(
    val email: String,
    val password: String,
    val name: String,
) : Parcelable

fun buildLoginBody(name: String, email: String, password: String) =
    LoginBody(name = name, email = email, password = password)

