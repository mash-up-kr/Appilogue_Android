package com.anonymous.appilogue.features.community.lounge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.community.detail.ReviewDetailViewModel
import com.anonymous.appilogue.features.community.lounge.ReviewListFragment.Companion.HOLE_KEY
import com.anonymous.appilogue.model.dto.LikeDto
import com.anonymous.appilogue.model.LikesModel
import com.anonymous.appilogue.model.ReportModel
import com.anonymous.appilogue.model.ReportType
import com.anonymous.appilogue.model.ReviewModel
import com.anonymous.appilogue.usecase.PlusLikeUseCase
import com.anonymous.appilogue.usecase.RemoveReviewUseCase
import com.anonymous.appilogue.usecase.ReportReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pageDataProvider: ReviewListDataProvider,
    private val plusLikeUseCase: PlusLikeUseCase,
    private val removeReviewUseCase: RemoveReviewUseCase,
    private val reportReviewUseCase: ReportReviewUseCase,
) : ViewModel() {
    val hole: String = savedStateHandle.get(HOLE_KEY) ?: ""

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchReviewList(): Flow<PagingData<ReviewModel>> {
        return pageDataProvider.getPagingData(hole)
            .cachedIn(viewModelScope)
            .catch { setErrorMessage(it.message) }
    }

    fun beginToLoad() {
        _isLoading.value = true
    }

    fun completeToLoad() {
        _isLoading.value = false
    }

    fun setErrorMessage(message: String?) {
        _errorMessage.value = message
    }

    fun plusLike(reviewModel: ReviewModel) {
        viewModelScope.launch {
            plusLikeUseCase(LikeDto(reviewModel.id))
        }
    }

    fun removeReview(reviewId: Int) {
        handleRemoveOrReport(true) {
            removeReviewUseCase(reviewId)
        }
    }

    fun reportReview(reviewId: Int) {
        handleRemoveOrReport(false) {
            val reportModel = ReportModel(ReportType.REVIEW.type, reviewId)
            reportReviewUseCase(reportModel)
        }
    }

    private fun handleRemoveOrReport(isMine: Boolean, block: suspend () -> UiState<*>) {
        viewModelScope.launch {
            val result = block()
            if (result.isSuccessful) {
                handleEvent(Event.ShowToastForResult(isMine))
            }
        }
    }

    fun plusLikeEvent(reviewModel: ReviewModel): Int {
        reviewModel.likes = reviewModel.likes + listOf(LikesModel())
        handleEvent(Event.PlusLike(reviewModel))
        return reviewModel.likes.size
    }

    private fun handleEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    sealed class Event {
        data class PlusLike(val reviewModel: ReviewModel) : Event()
        data class ShowToastForResult(val isMine: Boolean) : Event()
    }
}