package com.example.comunicaa.screens.home

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.repositories.cards.CardsRepository
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cardRepository: CardsRepositoryContract,
    private val userRepository: UserRepositoryContract
) :
    BaseViewModel() {

    val categories = SingleLiveData<List<Category>>()

    fun fetchCategories() {
        defaultLaunch {
            val user = userRepository.getUserData()
            if (user != null && !user.uid.isNullOrEmpty()) {
                val data = cardRepository.fetchCategories(user.uid)
                categories.postValue(data)
            } else {
                categories.postValue(Category.getMockData())
            }
        }
    }
}