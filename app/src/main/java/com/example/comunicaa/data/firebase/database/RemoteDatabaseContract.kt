package com.example.comunicaa.data.firebase.database

import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.models.user.UserModel

interface RemoteDatabaseContract {
    suspend fun insertUser(user: UserModel) : Boolean
    suspend fun deleteUser(user: UserModel) : Boolean

    suspend fun fetchCategories(userId: String): List<Category>
    suspend fun fetchCategories(): List<Category>
}