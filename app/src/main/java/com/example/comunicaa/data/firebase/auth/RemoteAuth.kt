package com.example.comunicaa.data.firebase.auth

import android.content.Context
import com.example.comunicaa.domain.models.user.LoginBody
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RemoteAuth @Inject constructor(@ApplicationContext private val context: Context) :
    RemoteAuthContract {

    override suspend fun login(body: LoginBody) {

    }

    override suspend fun register(body: LoginBody) {

    }

    override suspend fun logout() {

    }
}