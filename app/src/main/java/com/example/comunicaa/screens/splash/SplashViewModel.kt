package com.example.comunicaa.screens.splash

import android.os.Handler
import android.os.Looper
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    val showHomeScreen = SingleLiveData<Unit>()

    fun fetchData() {
        defaultLaunch {
            loadingSplashData()
        }
    }

    private fun loadingSplashData() {
        Handler(Looper.getMainLooper()).postDelayed({
            showHomeScreen.postValue(Unit)
        }, 2000)
    }
}