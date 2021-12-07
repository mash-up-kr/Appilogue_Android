package com.anonymous.appilogue.features.review

import androidx.lifecycle.*
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.model.ReviewDto
import com.anonymous.appilogue.usecase.RegisterReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewRegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val registerReviewUseCase: RegisterReviewUseCase,
) : ViewModel() {
    val appName = savedStateHandle.get<String>(APP_NAME_KEY)!!
    val appIconUrl = savedStateHandle.get<String>(APP_ICON_URL_KEY)!!
    val isBlackHoleReview = savedStateHandle.get<Boolean>(IS_BLACK_HOLE_REVIEW_KEY)!!
    val reviewEditText = MutableLiveData<String>()

    fun registerReview(
        hashtags: List<String>,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val hole = if (isBlackHoleReview) BLACK_HOLE else WHITE_HOLE
            val content = reviewEditText.value ?: return@launch
            val reviewDto = ReviewDto(hole, content, appName, appIconUrl, hashtags)
            val result = registerReviewUseCase(reviewDto)
            if (result.isSuccessful) {
                onSuccess()
            }
            reviewEditText.value = ""
        }
    }

    companion object {
        private const val APP_NAME_KEY = "appName"
        private const val APP_ICON_URL_KEY = "appIconUrl"
        private const val IS_BLACK_HOLE_REVIEW_KEY = "isBlackHoleReview"
        private const val BLACK_HOLE = "black"
        private const val WHITE_HOLE = "white"
    }
}