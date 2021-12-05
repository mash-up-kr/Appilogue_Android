package com.anonymous.appilogue.features.review

import android.graphics.Bitmap
import androidx.lifecycle.*
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.ImageApiResponse
import com.anonymous.appilogue.model.ReviewDto
import com.anonymous.appilogue.usecase.RegisterReviewUseCase
import com.anonymous.appilogue.usecase.UploadAppIconImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReviewRegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val registerReviewUseCase: RegisterReviewUseCase,
    private val uploadAppIconImage: UploadAppIconImage
) : ViewModel() {
    val isBlackHoleReview = savedStateHandle.get<Boolean>(IS_BLACK_HOLE_REVIEW_KEY)!!
    val reviewEditText = MutableLiveData<String>()

    private var appIconUrl: String = ""
    private var appIconUploaded = false

    fun uploadAppIcon(cacheDir: File, appIcon: Bitmap) {
        if (!appIconUploaded) {
            appIconUploaded = true

            viewModelScope.launch(Dispatchers.IO) {
                val result = uploadAppIconImage(cacheDir, appIcon)
                if (result.isSuccessful) {
                    appIconUrl = result.successOr(ImageApiResponse()).url
                }
            }
        }
    }

    fun registerReview(
        appName: String,
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
        const val IS_BLACK_HOLE_REVIEW_KEY = "isBlackHoleReview"
        private const val BLACK_HOLE = "black"
        private const val WHITE_HOLE = "white"
    }
}