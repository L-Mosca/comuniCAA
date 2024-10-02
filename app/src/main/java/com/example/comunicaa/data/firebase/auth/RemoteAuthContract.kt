package com.example.comunicaa.data.firebase.auth

import com.example.comunicaa.domain.models.user.LoginBody
import com.example.comunicaa.domain.models.user.UserModel

interface RemoteAuthContract {
    suspend fun login(body: LoginBody) : UserModel?
    suspend fun register(body: LoginBody) : UserModel?
    suspend fun logout()
}