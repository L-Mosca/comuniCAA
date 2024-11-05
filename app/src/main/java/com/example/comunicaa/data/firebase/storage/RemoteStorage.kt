package com.example.comunicaa.data.firebase.storage

import android.net.Uri
import com.example.comunicaa.utils.buildAudioDefaultMetadata
import com.example.comunicaa.utils.buildAudioName
import com.example.comunicaa.utils.buildImageName
import com.example.comunicaa.utils.pathToUri
import com.google.android.gms.tasks.Tasks
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

    override suspend fun insertImage(image: Uri, userId: String, cardId: String): String? {
        return suspendCoroutine { continuation ->
            val path = reference
                .child(USERS_STORAGE)
                .child(userId)
                .child(USER_ACTIONS)
                .child(cardId)
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


    override suspend fun removeActionFiles(userId: String, cardId: String): Boolean {
        return suspendCoroutine { continuation ->
            val path = "$USERS_STORAGE/$userId/$USER_ACTIONS/$cardId"
            reference.child(path).listAll()
                .addOnSuccessListener { list ->
                    val tasks = list.items.map { it.delete() }
                    Tasks.whenAll(tasks).addOnSuccessListener { continuation.resume(true) }
                        .addOnFailureListener { continuation.resume(false) }
                }
                .addOnFailureListener { continuation.resume(false) }
        }
    }

    override suspend fun insertAudio(audioPath: String, userId: String, cardId: String): String? {
        return suspendCoroutine { continuation ->
            val path = reference
                .child(USERS_STORAGE)
                .child(userId)
                .child(USER_ACTIONS)
                .child(cardId)
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
}