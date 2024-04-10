package com.example.hakaton.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _camera2Text = MutableLiveData<String>()
    val camera2Text: LiveData<String> = _camera2Text

    private val _opisanieText = MutableLiveData<String>()
    val opisanieText: LiveData<String> = _opisanieText

    fun setCamera2Text(text: String) {
        _camera2Text.value = text
    }

    fun setOpisanieText(text: String) {
        _opisanieText.value = text
    }
}

