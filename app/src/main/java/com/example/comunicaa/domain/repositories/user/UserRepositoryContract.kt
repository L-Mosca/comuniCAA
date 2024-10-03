package com.example.comunicaa.domain.repositories.user

import com.example.comunicaa.domain.models.user.LoginBody
import com.example.comunicaa.domain.models.user.RegisterBody
import com.example.comunicaa.domain.models.user.UserModel

interface UserRepositoryContract {
    suspend fun register(body: RegisterBody) : UserModel?
    suspend fun login(body: LoginBody) : UserModel?
    suspend fun googleLogin()
    suspend fun logout()

    suspend fun saveUserData(userModel: UserModel)
    suspend fun getUserData() : UserModel?
}