package com.anonymous.appilogue.features.community.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.dto.CommentDto
import com.anonymous.appilogue.model.CommentModel
import com.anonymous.appilogue.model.ReportModel
import com.anonymous.appilogue.model.ReportType
import com.anonymous.appilogue.usecase.FetchCommentsUseCase
import com.anonymous.appilogue.usecase.RegisterCommentUseCase
import com.anonymous.appilogue.usecase.RemoveCommentUseCase
import com.anonymous.appilogue.usecase.ReportCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CommentDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchCommentsUseCase: FetchCommentsUseCase,
    private val registerCommentUseCase: RegisterCommentUseCase,
    private val removeCommentUseCase: RemoveCommentUseCase,
    private val reportCommentUseCase: ReportCommentUseCase
) : ViewModel() {
    private val reviewId: Int = savedStateHandle.get<Int>(REVIEW_ID_KEY)!!
    private val commentId: Int = savedStateHandle.get<Int>(COMMENT_ID_KEY)!!

    private val _uiState: MutableStateFlow<UiState<List<CommentModel>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<CommentModel>>> = _uiState

    init {
        fetchComments()
    }

    val comments: StateFlow<List<CommentModel>> = uiState.mapLatest { state ->
        state.successOr(emptyList())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = emptyList()
    )

    val inputText: MutableLiveData<String?> = MutableLiveData()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    fun fetchComments() {
        viewModelScope.launch {
            fetchCommentsUseCase(reviewId, commentId).collect {
                _uiState.value = it
            }
        }
    }

    fun registerNestedComment(commentText: String) {
        viewModelScope.launch {
            val commentDto = CommentDto(reviewId, commentId, commentText)
            val result = registerCommentUseCase(commentDto)
            if (result.isSuccessful) {
                fetchComments()
            }
            inputText.value = ""
        }
    }

    fun getParentCommentId(): Int? = comments.value.find { it.parentId == null }?.id

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

    private fun handleRemoveOrReport(isMine: Boolean, block: suspend () -> UiState<*>) {
        viewModelScope.launch {
            val result = block()
            if (result.isSuccessful) {
                handleEvent(Event.ShowToastForResult(isMine))
            }
        }
    }

    fun addNestedCommentEvent() {
        inputText.value?.let {
            handleEvent(Event.AddNestedComment(it))
            inputText.value = null
        }
    }

    fun pressBackButtonEvent() {
        handleEvent(Event.PressBackButton)
    }

    fun removeCommentEvent(commentId: Int) {
        handleEvent(Event.RemoveComment(commentId))
    }

    fun reportCommentEvent(commentId: Int) {
        handleEvent(Event.ReportComment(commentId))
    }

    private fun handleEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    sealed class Event {
        data class AddNestedComment(val commentText: String) : Event()
        data class RemoveComment(val commentId: Int) : Event()
        data class ReportComment(val commentId: Int) : Event()
        object PressBackButton : Event()
        data class ShowToastForResult(val isMine: Boolean) : Event()
    }

    companion object {
        private const val REVIEW_ID_KEY = "reviewId"
        private const val COMMENT_ID_KEY = "commentId"
    }
}