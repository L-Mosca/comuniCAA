package com.example.comunicaa.domain.repositories.cards

import com.example.comunicaa.data.firebase.database.RemoteDatabaseContract
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.cards.Category
import javax.inject.Inject

class CardsRepository @Inject constructor(private val remoteDatabase: RemoteDatabaseContract) :
    CardsRepositoryContract {


    override suspend fun fetchCategories(userId: String): List<Category> {
        return remoteDatabase.fetchCategories()
    }

    override suspend fun fetchCategories(): List<Category> {
       return remoteDatabase.fetchCategories()
    }

    override suspend fun createAction(title: String, image: String, audio: String, userId: String) {
        val data = ActionCard.buildNewActionBody(title, image, audio, userId)
        remoteDatabase.createAction(data)
    }
}