package com.example.comunicaa.data.firebase.database

import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.models.cards.SubCategory
import com.example.comunicaa.domain.models.cards.toMap
import com.example.comunicaa.domain.models.user.UserModel
import com.google.firebase.database.ktx.database
import com.google.firebase.database.snapshots
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Suppress("UNCHECKED_CAST")
class RemoteDatabase @Inject constructor() : RemoteDatabaseContract {

    companion object {
        private const val USERS_BRANCH = "users"
        private const val USER_DATA = "user_data"

        private const val CATEGORIES = "categories"
        private const val USER_CATEGORIES = "user_categories"
        private const val SUBCATEGORIES = "subCategories"
        private const val ACTIONS = "actions"
    }

    private val database = Firebase.database.reference

    override suspend fun insertUser(user: UserModel): Boolean {
        val userId = user.uid
        if (userId.isNullOrEmpty()) return false

        return suspendCoroutine { continuation ->
            database.child(USERS_BRANCH).child(userId).child(USER_DATA).setValue(user.toMap())
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }
    }

    override suspend fun insertDefaultCategory(userId: String) {
        val category = Category.buildDefaultUserCategory(userId).toMap()

        database
            .child(USERS_BRANCH)
            .child(userId)
            .child(USER_CATEGORIES)
            .child(Category.DEFAULT_ID).setValue(category)

        insertDefaultSubcategory(userId)
    }

    override suspend fun insertDefaultSubcategory(userId: String) {
        val subCategory = SubCategory.buildDefaultUserSubcategory(userId).toMap()

        database
            .child(USERS_BRANCH)
            .child(userId)
            .child(USER_CATEGORIES)
            .child(Category.DEFAULT_ID)
            .child(SUBCATEGORIES)
            .child(SubCategory.DEFAULT_ID)
            .setValue(subCategory)
    }

    override suspend fun deleteUser(user: UserModel): Boolean {
        val userId = user.uid
        if (userId.isNullOrEmpty()) return false

        return suspendCoroutine { continuation ->
            database.child(USERS_BRANCH).child(userId).removeValue()
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }
    }

    override suspend fun deleteUserCard(userId: String, cardId: String) : Boolean {
        return suspendCoroutine { continuation ->
            database
                .child(USERS_BRANCH)
                .child(userId)
                .child(USER_CATEGORIES)
                .child(Category.DEFAULT_ID)
                .child(SUBCATEGORIES)
                .child(SubCategory.DEFAULT_ID)
                .child(ACTIONS)
                .child(cardId).removeValue()
                .addOnSuccessListener { continuation.resume(true) }
                .addOnFailureListener { continuation.resume(false) }
        }
    }

    override suspend fun fetchCategories(userId: String): List<Category> {
        return try {
            val ref = database.child(USERS_BRANCH).child(userId).child(CATEGORIES)
            val raw = ref.snapshots.first().value
            if (raw != null) Category.convertToCategoryList(raw as? HashMap<String, Any>)
            else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun fetchCategories(): List<Category> {
        return try {
            val ref = database.child(CATEGORIES)
            val raw = ref.snapshots.first().value
            if (raw != null) Category.convertToCategoryList(raw as? HashMap<String, Any>)
            else emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun fetchUserCards(userId: String): List<ActionCard> {
        val ref = database
            .child(USERS_BRANCH)
            .child(userId)
            .child(USER_CATEGORIES)
            .child(Category.DEFAULT_ID)
            .child(SUBCATEGORIES)
            .child(SubCategory.DEFAULT_ID)
            .child(ACTIONS)

        val raw = ref.snapshots.first().value

        return if (raw != null) ActionCard.convertToCardList(raw)
        else emptyList()
    }

    override suspend fun createAction(action: ActionCard) {
        val ref = database
            .child(USERS_BRANCH)
            .child(action.userId!!)
            .child(USER_CATEGORIES)
            .child(Category.DEFAULT_ID)
            .child(SUBCATEGORIES)
            .child(SubCategory.DEFAULT_ID)
            .child(ACTIONS).push()

        val id = ref.key
        action.id = id
        val data = action.toMap()

        ref.setValue(data)
    }
}