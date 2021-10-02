package com.anonymous.appilogue.features.review

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ReviewSelectorViewModel(
) : ViewModel() {

    private val _isBlackHoleSelected = MutableLiveData<Boolean>()
    val isBlackHoleSelected: LiveData<Boolean> = _isBlackHoleSelected

    private val _isWhiteHoleSelected = MutableLiveData<Boolean>()
    val isWhiteHoleSelected: LiveData<Boolean> = _isWhiteHoleSelected

    fun selectBlackHole() {
        _isBlackHoleSelected.value = true
        _isWhiteHoleSelected.value = false
    }

    fun selectWhiteHole() {
        _isWhiteHoleSelected.value = true
        _isBlackHoleSelected.value = false
    }
}