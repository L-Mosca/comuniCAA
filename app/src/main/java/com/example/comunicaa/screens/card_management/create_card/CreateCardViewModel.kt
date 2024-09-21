package com.example.comunicaa.screens.card_management.create_card

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.domain.models.keys.DataKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCardViewModel @Inject constructor(): BaseViewModel() {

    fun fetchCardData(keys: DataKeys?) {}
}