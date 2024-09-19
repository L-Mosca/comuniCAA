package com.example.comunicaa.screens.action_list

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.cards.ActionCard
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActionListViewModel @Inject constructor() : BaseViewModel() {

    val actionList = SingleLiveData<List<ActionCard>>()

    fun fetchActions() {
        defaultLaunch {
            val data = ActionCard.getMockData()
            actionList.postValue(data)
        }
    }
}