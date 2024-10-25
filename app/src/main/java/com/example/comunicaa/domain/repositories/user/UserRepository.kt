package com.example.comunicaa.domain.repositories.user

import com.example.comunicaa.data.firebase.auth.RemoteAuthContract
import com.example.comunicaa.data.firebase.database.RemoteDatabaseContract
import com.example.comunicaa.data.preferences_datastore.PreferencesContract
import com.example.comunicaa.domain.models.user.LoginBody
import com.example.comunicaa.domain.models.user.RegisterBody
import com.example.comunicaa.domain.models.user.UserModel
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val authService: RemoteAuthContract,
    private val databaseService: RemoteDatabaseContract,
    private val preferencesHelper: PreferencesContract,
) : UserRepositoryContract {

    override suspend fun register(body: RegisterBody): UserModel? {
        val userData = authService.register(body)
        if (userData != null) {
            preferencesHelper.saveUserData(userData)
            databaseService.insertUser(userData)
            userData.uid?.let { databaseService.insertDefaultCategory(it) }
        }
        return userData
    }

    override suspend fun login(body: LoginBody): UserModel? {
        val userData = authService.login(body)
        if (userData != null) preferencesHelper.saveUserData(userData)
        return userData
    }

    override suspend fun logout() {
        authService.logout()
        preferencesHelper.clearUserData()
    }

    override suspend fun saveUserData(userModel: UserModel) {
        preferencesHelper.saveUserData(userModel)
    }

    override suspend fun getUserData(): UserModel? {
        return preferencesHelper.getUserData()
    }

    override suspend fun googleLogin() {}
}