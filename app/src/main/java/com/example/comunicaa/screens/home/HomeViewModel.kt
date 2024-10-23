package com.example.comunicaa.screens.home

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardRepository: CardsRepositoryContract,
    private val userRepository: UserRepositoryContract
) : BaseViewModel() {

    val categories = SingleLiveData<List<Category>>()
    val userCategories = SingleLiveData<List<Category>>()

    fun fetchCategories() {
        defaultLaunch {
            categories.postValue(cardRepository.fetchCategories())
        }
    }

    fun fetchUserCategories() {
        defaultLaunch {
            val user = userRepository.getUserData()
            if (user != null && !user.uid.isNullOrEmpty()) {
                userCategories.postValue(cardRepository.fetchCategories(user.uid))
            }
        }
    }
}