package com.anonymous.appilogue.features.community.lounge

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(

) : ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun beginToLoad() {
        _isLoading.value = true
    }

    fun completeToLoad() {
        _isLoading.value = false
    }

    fun setErrorMessage(message: String) {
        _errorMessage.value = message
    }
}