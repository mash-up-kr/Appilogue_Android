package com.anonymous.appilogue.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.model.User
import com.anonymous.appilogue.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    private val _myBlackHoleReviewCount = MutableLiveData("0")
    val myBlackHoleReviewCount: LiveData<String> = _myBlackHoleReviewCount

    private val _myWhiteHoleReviewCount = MutableLiveData("0")
    val myWhiteHoleReviewCount: LiveData<String> = _myWhiteHoleReviewCount

    var nickname = MutableLiveData<String>()

    fun saveNickname(user: User) {
        viewModelScope.launch {
            nickname.value?.let {
                userRepository.saveMyInformation(user.copy(nickname = it))
            }
        }
    }
}