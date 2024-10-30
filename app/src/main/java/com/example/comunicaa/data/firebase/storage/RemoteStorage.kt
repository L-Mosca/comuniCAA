package com.example.comunicaa.data.firebase.storage

import android.net.Uri
import android.util.Base64
import com.example.comunicaa.utils.buildAudioDefaultMetadata
import com.example.comunicaa.utils.buildAudioName
import com.example.comunicaa.utils.buildImageName
import com.example.comunicaa.utils.pathToUri
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

    companion object {
        private const val USERS_STORAGE = "users"
        private const val USER_ACTIONS = "actions"
    }

    override suspend fun insertImage(image: Uri, userId: String): String? {
        return suspendCoroutine { continuation ->
            val path = reference
                .child(USERS_STORAGE)
                .child(userId)
                .child(USER_ACTIONS)
                .child(buildImageName())

            path.putFile(image).addOnCompleteListener {
                if (it.isSuccessful) {
                    path.downloadUrl.addOnCompleteListener { urlTask ->
                        if (urlTask.isSuccessful) continuation.resume(urlTask.result.toString())
                        else continuation.resume(null)
                    }
                } else continuation.resume(null)
            }
        }
    }

    override suspend fun updateImage(image: Uri, userId: String): Boolean {
        return true
    }

    override suspend fun removeImage(image: String): Boolean {
        return true
    }

    override suspend fun insertAudio(audioPath: String, userId: String): String? {
        return suspendCoroutine { continuation ->
            val path = reference
                .child(USERS_STORAGE)
                .child(userId)
                .child(USER_ACTIONS)
                .child(buildAudioName())

            val audioUri = pathToUri(audioPath)
            val audioMetadata = buildAudioDefaultMetadata()

            path.putFile(audioUri, audioMetadata).addOnCompleteListener {
                if (it.isSuccessful) {
                    path.downloadUrl.addOnCompleteListener { urlTask ->
                        if (urlTask.isSuccessful) continuation.resume(urlTask.result.toString())
                        else continuation.resume(null)
                    }
                } else continuation.resume(null)
            }
        }
    }

    override suspend fun updateAudio(audio: Base64): Boolean {
        return true
    }

    override suspend fun removeAudio(audio: String): Boolean {
        return true
    }
}