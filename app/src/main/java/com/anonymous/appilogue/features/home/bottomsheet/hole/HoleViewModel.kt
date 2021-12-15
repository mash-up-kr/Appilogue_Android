package com.anonymous.appilogue.features.home.bottomsheet.hole

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.ReviewModel
import com.anonymous.appilogue.usecase.FetchMyReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoleViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val fetchMyReviewsUseCase: FetchMyReviewsUseCase,
) : ViewModel() {
    private val holeText = savedStateHandle.get<String>(HOLE)!!

    private val _apps: MutableStateFlow<List<ReviewModel>> = MutableStateFlow(emptyList())
    val apps: StateFlow<List<ReviewModel>> = _apps

    init {
        fetchMyApps()
    }

    private fun fetchMyApps() {
        viewModelScope.launch {
            val result = fetchMyReviewsUseCase(holeText)
            if (result.isSuccessful) {
                _apps.value = result.successOr(emptyList())
                    .sortedBy { it.updatedAt }
                    .reversed()
                    .distinctBy { it.app.name }
            }
        }
    }

    companion object {
        private const val HOLE = "HOLE"
    }
}