package com.example.comunicaa.domain.repositories.cards

import com.example.comunicaa.domain.models.cards.Category

interface CardsRepositoryContract {
    suspend fun fetchCategories(userId: String) : List<Category>
    suspend fun fetchCategories(): List<Category>

    suspend fun createAction(title: String, image: String, audio: String, userId: String)
}