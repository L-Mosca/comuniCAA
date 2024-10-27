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
        val subCategory = SubCategory.buildDefaultUserSubcategory(userId).toMap()

        val categoryPath = "$USERS_BRANCH/$userId/${Category.DEFAULT_ID}"

        database.child(categoryPath).setValue(category).addOnSuccessListener {
            val subCategoryPath = "$USERS_BRANCH/$userId/${Category.DEFAULT_ID}/$SUBCATEGORIES"
            database.child(subCategoryPath).child(SubCategory.DEFAULT_ID).setValue(subCategory)
        }
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

    override suspend fun createAction(action: ActionCard) {
        val path = "/$USERS_BRANCH/${action.userId}/$USER_CATEGORIES/${Category.DEFAULT_ID}/${SubCategory.DEFAULT_ID}/actions"
        val ref = database.child(path).push()

        val id = ref.key
        action.id = id
        val data = action.toMap()

        ref.setValue(data)
    }
}