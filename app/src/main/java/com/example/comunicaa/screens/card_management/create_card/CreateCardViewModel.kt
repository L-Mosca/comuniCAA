package com.example.comunicaa.screens.card_management.create_card

import android.content.ContentResolver
import android.net.Uri
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.models.keys.DataKeys
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import com.example.comunicaa.utils.resizeImage
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCardViewModel @Inject constructor(
    private val cardRepository: CardsRepositoryContract,
    private val userRepository: UserRepositoryContract
) : BaseViewModel() {

    val createSuccess = SingleLiveData<Unit>()
    val createError = SingleLiveData<Unit>()
    val emptyDataError = SingleLiveData<Unit>()
    val initialCard = SingleLiveData<ActionCard>()

    fun fetchCardData(keys: DataKeys?) {
        defaultLaunch {
            keys?.let { data ->
                val card = cardRepository.fetchCardData(data.userId, data.actionId)
                if (card != null) initialCard.postValue(card!!)
            }
        }
    }

    private fun createAction(title: String, image: Uri, audio: String) {
        defaultLaunch(exceptionHandler = {
            setLoading(false)
            createError.postValue(Unit)
        }) {
            val user = userRepository.getUserData()
            if (user != null && !user.uid.isNullOrEmpty()) {
                cardRepository.createAction(title, image, audio, user.uid)
                createSuccess.postValue(Unit)
                setLoading(false)
            } else {
                createError.postValue(Unit)
                setLoading(false)
            }
        }
    }

    fun checkCardData(
        title: String,
        image: Uri?,
        audio: String?,
        contentResolver: ContentResolver,
    ) {
        setLoading(true)
        if (title.isNotEmpty() && image != null && !audio.isNullOrEmpty()) {
            val newImage = resizeImage(contentResolver, image)

            if (newImage != null) createAction(title, newImage, audio)
            else createError.postValue(Unit)

        } else emptyDataError.postValue(Unit)
    }
}