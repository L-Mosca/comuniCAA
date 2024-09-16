package com.example.comunicaa.di

import com.example.comunicaa.data.firebase.database.RemoteDatabase
import com.example.comunicaa.data.firebase.database.RemoteDatabaseContract
import com.example.comunicaa.data.firebase.storage.RemoteStorage
import com.example.comunicaa.data.firebase.storage.RemoteStorageContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataModule {

    @Singleton
    @Binds
    fun bindRemoteDatabase(remoteDatabase: RemoteDatabase): RemoteDatabaseContract

    @Singleton
    @Binds
    fun bindRemoteStorage(remoteStorage: RemoteStorage): RemoteStorageContract
}