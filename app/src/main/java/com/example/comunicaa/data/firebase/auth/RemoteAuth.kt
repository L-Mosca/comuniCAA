package com.example.comunicaa.data.firebase.auth

import android.util.Log
import com.example.comunicaa.domain.models.user.LoginBody
import com.example.comunicaa.domain.models.user.RegisterBody
import com.example.comunicaa.domain.models.user.UserModel
import com.example.comunicaa.domain.models.user.toUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteAuth @Inject constructor() : RemoteAuthContract {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override suspend fun login(body: LoginBody): UserModel? {
        Log.e("test", "usuario: $body")
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(body.email, body.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) continuation.resume(auth.currentUser.toUserModel())
                    else continuation.resume(null)
                }
        }
    }

    override suspend fun register(body: RegisterBody): UserModel? {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(body.email, body.password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(body.name)
                            .build()

                        user?.updateProfile(profileUpdates)?.addOnCompleteListener { profileTask ->
                            if (profileTask.isSuccessful) continuation.resume(auth.currentUser.toUserModel())
                            else continuation.resume(null)
                        }
                    } else continuation.resume(null)
                }
        }
    }

    override suspend fun logout() {

    }
}