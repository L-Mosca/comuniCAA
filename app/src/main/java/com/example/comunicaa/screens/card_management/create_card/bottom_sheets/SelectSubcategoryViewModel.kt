package com.example.comunicaa.screens.card_management.create_card.bottom_sheets

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.SubCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SelectSubcategoryViewModel @Inject constructor() : BaseViewModel() {

    val subcategories = SingleLiveData<List<SubCategory>>()

    fun fetchSubcategories() {
        defaultLaunch {
            val list = SubCategory.getMockData()
            subcategories.postValue(list)
        }
    }
}