package com.example.comunicaa.domain.repositories.user

import com.example.comunicaa.domain.models.user.LoginBody

interface UserRepositoryContract {
    suspend fun register(body: LoginBody)
    suspend fun login(body: LoginBody)
    suspend fun googleLogin()
    suspend fun logout()
}