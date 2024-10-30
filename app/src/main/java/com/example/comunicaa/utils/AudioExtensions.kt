package com.example.comunicaa.utils

import android.net.Uri
import com.google.firebase.storage.StorageMetadata
import java.io.File

class AudioUtil {
    companion object {
        const val AUDIO_PREFIX = "comunicaa_aud_"
        const val AUDIO_EXTENSION = "mp3"
        const val AUDIO_TYPE = "audio/mpeg"
    }
}

fun buildAudioName() : String {
    val timestamp = System.currentTimeMillis()
    return "${AudioUtil.AUDIO_PREFIX}$timestamp.${AudioUtil.AUDIO_EXTENSION}"
}

fun pathToUri(audioPath: String): Uri {
    val audioFile = File(audioPath)
    return Uri.fromFile(audioFile)
}

fun buildAudioDefaultMetadata() : StorageMetadata {
    return StorageMetadata.Builder()
        .setContentType(AudioUtil.AUDIO_TYPE)
        .build()
}