package com.example.comunicaa.screens.host

import android.view.MenuItem
import com.example.comunicaa.R
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import com.example.comunicaa.domain.models.user.UserModel
import com.example.comunicaa.domain.repositories.user.UserRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(private val userRepository: UserRepositoryContract) :
    BaseViewModel() {

    val openDrawer = SingleLiveData<Unit>()
    val closeDrawer = SingleLiveData<Unit>()
    val drawerIsOpen = SingleLiveData<Boolean>()
    val graphId = SingleLiveData<Int>()

    fun showDrawer() = openDrawer.postValue(Unit)
    fun hideDrawer() = closeDrawer.postValue(Unit)
    fun changeDrawerStatus(isOpen: Boolean) = drawerIsOpen.postValue(isOpen)

    fun showHome() = graphId.postValue(R.id.home_nav_graph)


    val user = SingleLiveData<UserModel?>()
    val changeScreen = SingleLiveData<Int>()
    val logoutSuccess = SingleLiveData<Unit>()

    fun getUserData() = defaultLaunch { user.postValue(userRepository.getUserData()) }

    fun handleNavigation(item: MenuItem) {
        when (item.itemId) {
            R.id.teste1 -> changeScreen.postValue(R.id.card_nav_graph)
            R.id.menuProfile -> {}
            R.id.menuLogout -> logout()
        }
    }

    private fun logout() {
        defaultLaunch {
            userRepository.logout()
            logoutSuccess.postValue(Unit)
        }
    }
}