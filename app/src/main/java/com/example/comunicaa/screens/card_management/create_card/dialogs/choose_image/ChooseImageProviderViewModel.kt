package com.example.comunicaa.screens.card_management.create_card.dialogs.choose_image

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResult
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseImageProviderViewModel @Inject constructor() : BaseViewModel() {

    val imageSuccess = SingleLiveData<Uri>()
    val loadImageUrl = SingleLiveData<String>()
    val imageError = SingleLiveData<Unit>()

    fun handleCameraImage(uri: Uri?, result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            if (uri != null) uri.let { imageSuccess.postValue(it) }
            else imageError.postValue(Unit)
        } else imageError.postValue(Unit)
    }

    fun handleGalleryImage(uri: Uri?) {
        if (uri != null) uri.let { imageSuccess.postValue(it) }
        else imageError.postValue(Unit)
    }

    fun handleInitialData(imageUri: Uri?, imageUrl: String?) {
        defaultLaunch {
            if (imageUri != null) {
                imageSuccess.postValue(imageUri!!)
            } else if (!imageUrl.isNullOrEmpty()) {
                loadImageUrl.postValue(imageUrl!!)
            }
        }
    }
}