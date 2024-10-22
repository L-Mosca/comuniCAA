package com.example.comunicaa.screens.card_management.create_card.dialogs.choose_audio

import com.example.comunicaa.R
import com.example.comunicaa.base.BaseViewModel
import com.example.comunicaa.base.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChooseAudioViewModel @Inject constructor() : BaseViewModel() {

    val setInitialPath = SingleLiveData<String?>()
    val buttonStyle = SingleLiveData<Pair<Int, Int>>()

    fun changeButtonAppearance(startRecording: Boolean) {
        val buttonIcon = if (startRecording) R.drawable.ic_stop else R.drawable.ic_mic
        val buttonText = if (startRecording) R.string.stop_recording else R.string.record_audio
        buttonStyle.postValue(Pair(buttonIcon, buttonText))
    }

    fun handleInitialData(data: String?) {
        setInitialPath.postValue(data)
    }
}