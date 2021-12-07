package com.anonymous.appilogue.features.community.lounge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anonymous.appilogue.features.community.lounge.ReviewListFragment.Companion.HOLE_KEY
import com.anonymous.appilogue.model.LikeDto
import com.anonymous.appilogue.model.LikesModel
import com.anonymous.appilogue.model.ReviewInfo
import com.anonymous.appilogue.usecase.PlusLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val pageDataProvider: ReviewListDataProvider,
    private val plusLikeUseCase: PlusLikeUseCase
) : ViewModel() {
    val hole: String = savedStateHandle.get(HOLE_KEY) ?: ""

    private val _event = MutableSharedFlow<Event>()
    val event: SharedFlow<Event> = _event

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchReviewList(): Flow<PagingData<ReviewInfo>> {
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

    fun plusLike(reviewInfo: ReviewInfo) {
        viewModelScope.launch {
            plusLikeUseCase(LikeDto(reviewInfo.id))
        }
    }

    fun plusLikeEvent(reviewInfo: ReviewInfo): Int {
        reviewInfo.likes = reviewInfo.likes + listOf(LikesModel())
        handleEvent(Event.PlusLike(reviewInfo))
        return reviewInfo.likes.size
    }

    private fun handleEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    sealed class Event {
        data class PlusLike(val reviewInfo: ReviewInfo) : Event()
    }
}