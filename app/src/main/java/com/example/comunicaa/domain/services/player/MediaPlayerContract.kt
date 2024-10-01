package com.example.comunicaa.domain.services.player

import android.content.Context

interface MediaPlayerContract {
    fun init(context: Context)
    fun play(url: String)
    fun stop()
}