package com.anonymous.appilogue.features.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReviewSelectorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val appName = savedStateHandle.get<String>(APP_NAME_KEY)!!
    val appIconUrl = savedStateHandle.get<String>(APP_ICON_URL_KEY)!!

    private val _isBlackHoleSelected = MutableStateFlow(false)
    val isBlackHoleSelected: StateFlow<Boolean> = _isBlackHoleSelected

    private val _isWhiteHoleSelected = MutableStateFlow(false)
    val isWhiteHoleSelected: StateFlow<Boolean> = _isWhiteHoleSelected

    fun selectBlackHole() {
        _isBlackHoleSelected.value = true
        _isWhiteHoleSelected.value = false
    }

    fun selectWhiteHole() {
        _isWhiteHoleSelected.value = true
        _isBlackHoleSelected.value = false
    }

    fun isSelected(): Boolean {
        return isBlackHoleSelected.value || isWhiteHoleSelected.value
    }

    companion object {
        private const val APP_NAME_KEY = "appName"
        private const val APP_ICON_URL_KEY = "appIconUrl"
    }
}
