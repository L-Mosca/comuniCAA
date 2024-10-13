package com.example.comunicaa.data.firebase.storage

import android.util.Base64

interface RemoteStorageContract {
    suspend fun insertImage(image: Base64): Boolean
    suspend fun updateImage(image: Base64): Boolean
    suspend fun removeImage(image: String): Boolean

    suspend fun insertAudio(audio: Base64): Boolean
    suspend fun updateAudio(audio: Base64): Boolean
    suspend fun removeAudio(audio: String): Boolean
}