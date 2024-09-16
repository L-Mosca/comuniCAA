package com.example.comunicaa.di

import com.example.comunicaa.domain.repositories.cards.CardsRepository
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
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
    fun bindUserRepository(userRepository: UserRepositoryContract): UserRepositoryContract
}