package com.example.comunicaa.di

import com.example.comunicaa.data.firebase.auth.RemoteAuth
import com.example.comunicaa.data.firebase.auth.RemoteAuthContract
import com.example.comunicaa.data.firebase.database.RemoteDatabase
import com.example.comunicaa.data.firebase.database.RemoteDatabaseContract
import com.example.comunicaa.data.firebase.storage.RemoteStorage
import com.example.comunicaa.data.firebase.storage.RemoteStorageContract
import com.example.comunicaa.domain.repositories.cards.CardsRepository
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
import com.example.comunicaa.domain.repositories.user.UserRepository
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Singleton
    @Binds
    fun bindCardsRepository(cardsRepository: CardsRepository): CardsRepositoryContract

    @Singleton
    @Binds
    fun bindUserRepository(userRepository: UserRepository): UserRepositoryContract

    @Singleton
    @Binds
    fun bindRemoteDatabase(remoteDatabase: RemoteDatabase): RemoteDatabaseContract

    @Singleton
    @Binds
    fun bindRemoteStorage(remoteStorage: RemoteStorage): RemoteStorageContract

    @Singleton
    @Binds
    fun bindRemoteAuth(removeAuth: RemoteAuth): RemoteAuthContract
}