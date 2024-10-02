package com.example.comunicaa.data.firebase.auth

import com.example.comunicaa.domain.models.user.LoginBody

interface RemoteAuthContract {
    suspend fun login(body: LoginBody)
    suspend fun register(body: LoginBody)
    suspend fun logout()
}