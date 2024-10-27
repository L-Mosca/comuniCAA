package com.example.comunicaa.screens.card_management.create_card

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.core.net.toUri
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.keys.DataKeys
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCardViewModel @Inject constructor(
    private val cardRepository: CardsRepositoryContract,
    private val userRepository: UserRepositoryContract
) : BaseViewModel() {

    val createSuccess = SingleLiveData<Unit>()
    val createError = SingleLiveData<Unit>()

    fun fetchCardData(keys: DataKeys?) {}

    fun createAction(title: String, image: Uri?, audio: String?) {
        defaultLaunch(exceptionHandler = { createError.postValue(Unit) }) {
            val user = userRepository.getUserData()
            if (user != null && !user.uid.isNullOrEmpty()) {
                cardRepository.createAction(title, image ?: "".toUri() , audio!! , user.uid)
                createSuccess.postValue(Unit)
            } else createError.postValue(Unit)
        }
    }
}