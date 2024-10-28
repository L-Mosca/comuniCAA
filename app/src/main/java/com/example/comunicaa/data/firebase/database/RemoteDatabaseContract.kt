package com.example.comunicaa.data.firebase.database

import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.models.user.UserModel

interface RemoteDatabaseContract {
    suspend fun insertUser(user: UserModel): Boolean
    suspend fun insertDefaultCategory(userId: String)
    suspend fun insertDefaultSubcategory(userId: String)
    suspend fun deleteUser(user: UserModel): Boolean
    suspend fun deleteUserCard(userId: String, cardId: String) : Boolean

    suspend fun fetchCategories(userId: String): List<Category>
    suspend fun fetchCategories(): List<Category>
    suspend fun fetchUserCards(userId: String): List<ActionCard>

    suspend fun createAction(action: ActionCard)
}