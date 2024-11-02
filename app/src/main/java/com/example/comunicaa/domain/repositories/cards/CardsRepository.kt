package com.example.comunicaa.domain.repositories.cards

import android.net.Uri
import com.example.comunicaa.data.firebase.database.RemoteDatabaseContract
import com.example.comunicaa.data.firebase.storage.RemoteStorageContract
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.cards.Category
import javax.inject.Inject

class CardsRepository @Inject constructor(
    private val remoteDatabase: RemoteDatabaseContract,
    private val remoteStorage: RemoteStorageContract,
) : CardsRepositoryContract {


    override suspend fun fetchCategories(userId: String): List<Category> {
        return remoteDatabase.fetchCategories()
    }

    override suspend fun fetchCategories(): List<Category> {
        return remoteDatabase.fetchCategories()
    }

    override suspend fun fetchUserCards(userId: String): List<ActionCard> {
        return remoteDatabase.fetchUserCards(userId)
    }

    override suspend fun deleteUserCard(userId: String, cardId: String) : Boolean {
        val removeDatabase = remoteDatabase.deleteUserCard(userId, cardId)
        remoteStorage.removeActionFiles(userId, cardId)
        return removeDatabase
    }

    override suspend fun fetchCardData(userId: String, cardId: String) : ActionCard? {
        return remoteDatabase.fetchCardData(userId, cardId)
    }

    override suspend fun createAction(title: String, image: Uri, audio: String, userId: String) {
        val cardId = remoteDatabase.getNewCardId(userId)

        if (cardId.isNotEmpty()) {
            val imagePath = remoteStorage.insertImage(image, userId, cardId)
            val audioPath = remoteStorage.insertAudio(audio, userId, cardId)
            if (imagePath != null && audioPath != null) {
                val newAction = ActionCard.buildNewActionBody(title, imagePath, audioPath, userId, cardId)
                remoteDatabase.createAction(newAction)
            } else throw Exception()
        } else throw Exception()
    }
}