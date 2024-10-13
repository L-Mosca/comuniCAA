package com.example.comunicaa.screens.home

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.Category
import com.example.comunicaa.domain.repositories.cards.CardsRepository
import com.example.comunicaa.domain.repositories.cards.CardsRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val cardRepository: CardsRepositoryContract) :
    BaseViewModel() {

    val categories = SingleLiveData<List<Category>>()

    fun fetchCategories() {
        defaultLaunch { categories.postValue(Category.getMockData()) }
    }

    fun test() {
        defaultLaunch {

        }
    }
}