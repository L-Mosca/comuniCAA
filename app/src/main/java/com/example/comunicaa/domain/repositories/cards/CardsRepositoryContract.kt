package com.example.comunicaa.domain.repositories.cards

import android.net.Uri
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.cards.Category

interface CardsRepositoryContract {
    suspend fun fetchCategories(userId: String) : List<Category>
    suspend fun fetchCategories(): List<Category>
    suspend fun fetchUserCards(userId: String): List<ActionCard>
    suspend fun deleteUserCard(userId: String, cardId: String) : Boolean
    suspend fun fetchCardData(userId: String, cardId: String) : ActionCard?

    suspend fun createAction(title: String, image: Uri, audio: String, userId: String)
}