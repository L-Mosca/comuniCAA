package com.example.comunicaa.data.preferences_datastore

import com.example.comunicaa.domain.models.user.UserModel

interface PreferencesContract {

    suspend fun getRemoteDbVersion(): String?
    suspend fun saveRemoteDbVersion(version: String)

    suspend fun getUserData(): UserModel?
    suspend fun clearUserData()
    suspend fun saveUserData(userModel: UserModel)
}