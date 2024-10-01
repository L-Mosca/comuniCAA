package com.example.comunicaa.domain.services.player

import android.content.Context
import android.media.MediaPlayer
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MediaPlayer @Inject constructor(@ApplicationContext private val context: Context) :
    MediaPlayerContract {

    private var mediaPlayer: MediaPlayer? = null

    override fun init(context: Context) {
        mediaPlayer?.release()
        if (mediaPlayer == null) mediaPlayer = MediaPlayer()
        mediaPlayer?.isLooping = false
    }

    override fun play(url: String) {
        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepare()
        mediaPlayer?.start()
    }

    override fun stop() {
        mediaPlayer?.let { if (it.isPlaying) it.stop() }
    }
}