package com.example.comunicaa.screens.host

import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor() : BaseViewModel() {

    val openDrawer = SingleLiveData<Unit>()
    val closeDrawer = SingleLiveData<Unit>()
    val drawerIsOpen = SingleLiveData<Boolean>()

    fun showDrawer() = openDrawer.postValue(Unit)
    fun hideDrawer() = closeDrawer.postValue(Unit)
    fun changeDrawerStatus(isOpen: Boolean) = drawerIsOpen.postValue(isOpen)
}