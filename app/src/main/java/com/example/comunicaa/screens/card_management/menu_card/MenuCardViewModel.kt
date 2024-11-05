package com.example.comunicaa.screens.card_management.menu_card

import com.example.comunicaa.R
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.ActionCard
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuCardViewModel @Inject constructor(
    private val userRepository: UserRepositoryContract,
    private val cardRepository: CardsRepositoryContract,
) : BaseViewModel() {

    val userCards = SingleLiveData<List<ActionCard>>()
    val deleteCardError = SingleLiveData<Unit>()
    val editAction = SingleLiveData<ActionCard?>()

    fun fetchData() {
        defaultLaunch {
            val user = userRepository.getUserData()
            if (user != null && !user.uid.isNullOrEmpty()) {
                userCards.postValue(cardRepository.fetchUserCards(user.uid))
            }
        }
    }

    fun deleteCard(userId: String?, cardId: String?) {
        defaultLaunch(exceptionHandler = { deleteCardError.postValue(Unit) }) {
            val deleteCardSuccess = cardRepository.deleteUserCard(userId!!, cardId!!)

            if (deleteCardSuccess) fetchData()
            else deleteCardError.postValue(Unit)
        }
    }

    fun handleMenuItem(itemId: Int, card: ActionCard) : Boolean {
        when (itemId) {
            R.id.menuEditCard -> editAction.postValue(card)
            R.id.menuDeleteCard -> deleteCard(card.userId, card.id)
        }
        return true
    }
}