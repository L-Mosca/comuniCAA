package com.example.comunicaa.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel() {

    val errorMessage: LiveData<Any>
        get() = mErrorMessage
    private val mErrorMessage = MutableLiveData<Any>()

    val loading: LiveData<Boolean>
        get() = mLoading
    private val mLoading = MutableLiveData<Boolean>()

    protected fun <T> LiveData<T>.post(data: T) {
        if (this is MutableLiveData<T>) {
            postValue(data)
        }
    }

    fun setLoading(isLoading: Boolean) {
        mLoading.postValue(isLoading)
    }


    fun defaultLaunch(
        exceptionHandler: ((Throwable) -> Unit)? = null,
        @StringRes defaultErrorMessage: Int? = null,
        loaderLiveData: MutableLiveData<Boolean>? = mLoading,
        dispatcher: CoroutineContext = EmptyCoroutineContext,
        function: suspend () -> Unit
    ) {
        viewModelScope.launch(dispatcher) {
            loaderLiveData?.postValue(true)

            try {
                function.invoke()
            } catch (throwable: Throwable) {
                throwable.printStackTrace()
                defaultCatch(throwable, exceptionHandler, defaultErrorMessage)
            }
            loaderLiveData?.postValue(false)
        }
    }

    private fun defaultCatch(
        throwable: Throwable,
        exceptionHandler: ((Throwable) -> Unit)? = null,
        defaultErrorMessage: Int? = null
    ) {
        when {
            exceptionHandler != null -> exceptionHandler.invoke(throwable)
            defaultErrorMessage != null -> defaultErrorMessage.let { mErrorMessage.postValue(it) }
        }
    }

}