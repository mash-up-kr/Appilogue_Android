package com.anonymous.appilogue.features.community.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.*
import com.anonymous.appilogue.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchReviewUseCase: FetchReviewUseCase,
    private val registerCommentUseCase: RegisterCommentUseCase,
    private val removeReviewUseCase: RemoveReviewUseCase,
    private val reportReviewUseCase: ReportReviewUseCase,
    private val removeCommentUseCase: RemoveCommentUseCase,
    private val reportCommentUseCase: ReportCommentUseCase,
    private val plusLikeUseCase: PlusLikeUseCase
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

    val inputText: MutableStateFlow<String?> = MutableStateFlow(null)

    fun fetchReviews() {
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

    fun removeReview() {
        handleRemoveOrReport(true) {
            removeReviewUseCase(reviewId)
        }
    }

    fun reportReview() {
        handleRemoveOrReport(false) {
            val reportModel = ReportModel(ReportType.REVIEW.type, reviewId)
            reportReviewUseCase(reportModel)
        }
    }

    fun removeComment(commentId: Int) {
        handleRemoveOrReport(true) {
            removeCommentUseCase(commentId)
        }
    }

    fun reportComment(commentId: Int) {
        handleRemoveOrReport(false) {
            val reportModel = ReportModel(ReportType.COMMENT.type, commentId)
            reportCommentUseCase(reportModel)
        }
    }

    fun addCommentEvent() {
        inputText.value?.let {
            handleEvent(Event.AddComment(it))
            inputText.value = null
        }
    }

    fun plusLike(reviewInfo: ReviewInfo) {
        viewModelScope.launch {
            plusLikeUseCase(LikeDto(reviewInfo.id))
        }
    }

    fun getAuthorId(): Int = reviewInfo.value.user.id

    private fun handleRemoveOrReport(isMine: Boolean, block: suspend () -> UiState<*>) {
        viewModelScope.launch {
            val result = block()
            if (result.isSuccessful) {
                handleEvent(Event.ShowToastForResult(isMine))
            }
        }
    }

    fun pressBackButtonEvent() {
        handleEvent(Event.PressBackButton)
    }

    fun removeReviewEvent() {
        handleEvent(Event.RemoveReview)
    }

    fun reportReviewEvent() {
        handleEvent(Event.ReportReview)
    }

    fun removeCommentEvent(commentId: Int) {
        handleEvent(Event.RemoveComment(commentId))
    }

    fun reportCommentEvent(commentId: Int) {
        handleEvent(Event.ReportComment(commentId))
    }

    fun moveToAppInfoEvent() {
        handleEvent(Event.MoveToAppInfo(reviewInfo.value.app))
    }

    fun plusLikeEvent(): Int {
        reviewInfo.value.likes = reviewInfo.value.likes + listOf(LikesModel())
        handleEvent(Event.PlusLike(reviewInfo.value))

        return reviewInfo.value.likes.size
    }

    private fun handleEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    sealed class Event {
        data class AddComment(val commentText: String) : Event()
        data class RemoveComment(val commentId: Int) : Event()
        data class ReportComment(val commentId: Int) : Event()
        object RemoveReview : Event()
        object ReportReview : Event()
        object PressBackButton : Event()
        data class ShowToastForResult(val isMine: Boolean) : Event()
        data class MoveToAppInfo(val appInfo: AppModel) : Event()
        data class PlusLike(val reviewInfo: ReviewInfo) : Event()
    }

    companion object {
        const val REVIEW_ID_KEY = "reviewId"
    }
}