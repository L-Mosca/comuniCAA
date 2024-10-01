package com.example.comunicaa.di

import android.content.Context
import com.example.comunicaa.domain.services.firebase.FirebaseService
import com.example.comunicaa.domain.services.firebase.FirebaseServiceContract
import com.example.comunicaa.domain.services.player.MediaPlayer
import com.example.comunicaa.domain.services.player.MediaPlayerContract
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideMediaPlayerService(@ApplicationContext context: Context): MediaPlayerContract {
        return MediaPlayer(context)
    }

    @Provides
    @Singleton
    fun provideFirebaseService(firebaseService: FirebaseService): FirebaseServiceContract =
        firebaseService
}