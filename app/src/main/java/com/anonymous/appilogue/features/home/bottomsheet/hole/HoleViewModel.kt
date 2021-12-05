package com.anonymous.appilogue.features.home.bottomsheet.hole

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.model.Review
import com.anonymous.appilogue.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HoleViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {
    private val _apps = MutableLiveData<List<Review>>()
    val apps: LiveData<List<Review>> = _apps

    fun fetchMyBlackHoleApps() {
        viewModelScope.launch {
            _apps.value = reviewRepository.fetchMyBlackHoleReview()
        }
    }

    fun fetchMyWhiteHoleApps() {
        viewModelScope.launch {
            _apps.value = reviewRepository.fetchMyWhiteHoleReview()
        }
    }
}