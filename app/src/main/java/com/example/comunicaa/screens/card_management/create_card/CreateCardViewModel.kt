package com.example.comunicaa.screens.card_management.create_card

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResult
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.domain.models.keys.DataKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateCardViewModel @Inject constructor() : BaseViewModel() {


    fun fetchCardData(keys: DataKeys?) {}

    fun handleImageResult(result: ActivityResult) {
        val imageBitmap = result.data?.data
        Log.e("test", "test camera image: $imageBitmap")
        if (result.resultCode == Activity.RESULT_OK) {
            Log.e("test", "result code is ok")
            // TODO set camera image here
        } else {
            //TODO show image error
        }
    }
}