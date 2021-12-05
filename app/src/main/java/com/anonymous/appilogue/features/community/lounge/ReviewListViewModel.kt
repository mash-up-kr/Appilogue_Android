package com.anonymous.appilogue.features.community.lounge

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.anonymous.appilogue.features.community.lounge.ReviewListFragment.Companion.HOLE_KEY
import com.anonymous.appilogue.model.ReviewInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel @Inject constructor(
    private val pageDataProvider: ReviewListDataProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val hole: String = savedStateHandle.get(HOLE_KEY) ?: ""

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
}