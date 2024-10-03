package com.example.comunicaa.domain.models.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterBody(
    val email: String,
    val password: String,
    val name: String,
) : Parcelable

fun buildRegisterBody(name: String, email: String, password: String) =
    RegisterBody(name = name, email = email, password = password)

