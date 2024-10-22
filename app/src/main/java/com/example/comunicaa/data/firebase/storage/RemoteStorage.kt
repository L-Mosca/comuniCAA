package com.example.comunicaa.data.firebase.storage

import android.util.Base64
import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoteStorage @Inject constructor() : RemoteStorageContract {

    private val storage: FirebaseStorage by lazy { Firebase.storage }
    private val reference: StorageReference by lazy { storage.reference }

    override suspend fun insertImage(image: Base64): Boolean {
        return suspendCoroutine { continuation ->
            reference.child("test").putFile("".toUri()).addOnCompleteListener {
                if (it.isSuccessful) continuation.resume(true)
                else continuation.resume(false)
            }
        }
    }

    override suspend fun updateImage(image: Base64): Boolean {
        return true
    }

    override suspend fun removeImage(image: String): Boolean {
        return true
    }

    override suspend fun insertAudio(audio: Base64): Boolean {
        return true
    }

    override suspend fun updateAudio(audio: Base64): Boolean {
        return true
    }

    override suspend fun removeAudio(audio: String): Boolean {
        return true
    }
}