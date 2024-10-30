package com.example.comunicaa.data.firebase.storage

import android.net.Uri
import android.util.Base64

interface RemoteStorageContract {
    suspend fun insertImage(image: Uri, userId: String): String?
    suspend fun updateImage(image: Uri, userId: String): Boolean
    suspend fun removeImage(image: String): Boolean

    suspend fun insertAudio(audioPath: String, userId: String): String?
    suspend fun updateAudio(audio: Base64): Boolean
    suspend fun removeAudio(audio: String): Boolean
}