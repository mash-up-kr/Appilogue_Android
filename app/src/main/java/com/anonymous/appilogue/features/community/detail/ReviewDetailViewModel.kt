package com.anonymous.appilogue.features.community.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.CommentDto
import com.anonymous.appilogue.model.ReviewInfo
import com.anonymous.appilogue.usecase.FetchReviewUseCase
import com.anonymous.appilogue.usecase.RegisterCommentUseCase
import com.anonymous.appilogue.usecase.RemoveCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchReviewUseCase: FetchReviewUseCase,
    private val registerCommentUseCase: RegisterCommentUseCase,
    private val removeCommentUseCase: RemoveCommentUseCase,
): ViewModel() {
    val reviewId: Int = savedStateHandle.get<Int>(REVIEW_ID_KEY)!!

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event

    private val _uiState: MutableStateFlow<UiState<ReviewInfo>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<ReviewInfo>> = _uiState

    init {
        fetchReviews()
    }

    val reviewInfo: StateFlow<ReviewInfo> = uiState.mapLatest { state ->
        state.successOr(ReviewInfo())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ReviewInfo()
    )

    val inputText: MutableLiveData<String?> = MutableLiveData()

    private fun fetchReviews() {
        viewModelScope.launch {
            fetchReviewUseCase(reviewId).collect {
                _uiState.value = it
            }
        }
    }

    fun registerComment(commentText: String) {
        viewModelScope.launch {
            val commentDto = CommentDto(reviewInfo.value.id, null, commentText)
            val result = registerCommentUseCase(commentDto)
            if (result.isSuccessful) {
                fetchReviews()
            }
        }
    }

    fun addComment() {
        inputText.value?.let {
            handleEvent(Event.AddComment(it))
            inputText.value = ""
        }
    }

    private fun handleEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    sealed class Event {
        data class AddComment(val commentText: String) : Event()
        data class RemoveComment(val commentId: Int): Event()
        object RemoveReview : Event()
    }

    companion object {
        const val REVIEW_ID_KEY = "reviewId"
    }
}