package com.example.comunicaa.data.firebase.storage

import android.net.Uri

interface RemoteStorageContract {
    suspend fun insertImage(image: Uri, userId: String, cardId: String): String?
    suspend fun insertAudio(audioPath: String, userId: String, cardId: String): String?
    suspend fun removeActionFiles(userId: String, cardId: String): Boolean
}