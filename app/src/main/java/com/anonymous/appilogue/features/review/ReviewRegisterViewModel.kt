package com.anonymous.appilogue.features.review

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReviewRegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val isBlackHoleReview = savedStateHandle.get<Boolean>(IS_BLACK_HOLE_REVIEW_KEY)
    val reviewEditText = MutableLiveData<String>()

    companion object {
        const val IS_BLACK_HOLE_REVIEW_KEY = "isBlackHoleReview"
    }
}