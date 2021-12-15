package com.anonymous.appilogue.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.User
import com.anonymous.appilogue.repository.UserRepository
import com.anonymous.appilogue.usecase.FetchMyReviewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val fetchMyReviewsUseCase: FetchMyReviewsUseCase
) : ViewModel() {

    private val _myBlackHoleReviewCount: MutableStateFlow<String> = MutableStateFlow("0")
    val myBlackHoleReviewCount: StateFlow<String> = _myBlackHoleReviewCount

    private val _myWhiteHoleReviewCount: MutableStateFlow<String> = MutableStateFlow("0")
    val myWhiteHoleReviewCount: StateFlow<String> = _myWhiteHoleReviewCount

    var nickname = MutableLiveData<String>()

    init {
        fetchMyReviewCount()
    }

    fun saveNickname(user: User) {
        viewModelScope.launch {
            nickname.value?.let {
                userRepository.saveMyInformation(user.copy(nickname = it))
            }
        }
    }

    fun fetchMyReviewCount() {
        viewModelScope.launch {
            val result = fetchMyReviewsUseCase(null)
            if (result.isSuccessful) {
                val reviews = result.successOr(emptyList())
                _myBlackHoleReviewCount.value = reviews.count { it.hole == "black" }.toString()
                _myWhiteHoleReviewCount.value = reviews.count { it.hole == "white" }.toString()
            }
        }
    }
}