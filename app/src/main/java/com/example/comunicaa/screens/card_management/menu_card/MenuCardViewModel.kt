package com.example.comunicaa.screens.card_management.menu_card

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuCardViewModel @Inject constructor() : BaseViewModel() {

    val categories = SingleLiveData<List<Category>>()

    fun fetchData() {
        defaultLaunch { categories.postValue(Category.getMockData()) }
    }
}