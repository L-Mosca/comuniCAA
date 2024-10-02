package com.example.comunicaa.domain.repositories.user

import com.example.comunicaa.data.firebase.auth.RemoteAuthContract
import com.example.comunicaa.domain.models.user.LoginBody
import javax.inject.Inject

class UserRepository @Inject constructor(private val authRepository: RemoteAuthContract) :
    UserRepositoryContract {

    override suspend fun register(body: LoginBody) = authRepository.register(body)

    override suspend fun login(body: LoginBody) = authRepository.login(body)

    override suspend fun logout() = authRepository.logout()

    override suspend fun googleLogin() {}
}