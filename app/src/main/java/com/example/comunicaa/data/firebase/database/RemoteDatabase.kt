package com.example.comunicaa.data.firebase.database

import com.example.comunicaa.domain.models.user.UserModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteDatabase @Inject constructor() : RemoteDatabaseContract {

    companion object {
        private const val USERS_BRANCH = "users"
        private const val USER_DATA = "user_data"
    }

    private val database = Firebase.database.reference

    override suspend fun insertUser(user: UserModel): Boolean {
        val userId = user.uid
        if (userId.isNullOrEmpty()) return false

        return suspendCoroutine { continuation ->
            database.child(USERS_BRANCH).child(userId).child(USER_DATA).setValue(user.toMap())
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }
    }

    override suspend fun deleteUser(user: UserModel): Boolean {
        val userId = user.uid
        if (userId.isNullOrEmpty()) return false

        return suspendCoroutine { continuation ->
            database.child(USERS_BRANCH).child(userId).removeValue()
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }
    }
}