package com.example.comunicaa.screens.home

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {

    val categories = SingleLiveData<List<Category>>()

    fun fetchCategories() {
        defaultLaunch { categories.postValue(Category.getMockData()) }
    }
}