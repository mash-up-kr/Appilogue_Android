package com.anonymous.appilogue.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.model.User
import com.anonymous.appilogue.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
class MainViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
    var selectedApp: InstalledApp? = null
    private val _bottomNavigationState = MutableLiveData(true)
    val bottomNavigationState: LiveData<Boolean> = _bottomNavigationState

    private val _bottomNavigationClickable = MutableLiveData(true)
    val bottomNavigationClickable: LiveData<Boolean> = _bottomNavigationClickable

    private val _myUser = MutableLiveData<User>()
    val myUser: LiveData<User> = _myUser

    fun hideBottomNavigation() {
        _bottomNavigationState.value = false
    }

    fun showBottomNavigation() {
        _bottomNavigationState.value = true
    }

    fun enableClickBottomNavigation() {
        _bottomNavigationClickable.value = true
    }

    fun disableClickBottomNavigation() {
        _bottomNavigationClickable.value = false
    }

    fun fetchMyInformation() {
        viewModelScope.launch {
            userRepository.fetchMyInformation()?.let {
                _myUser.value = it
            }
        }
    }

    fun editNickname(nickname: String) {
        _myUser.value = _myUser.value?.copy(nickname = nickname)
    }

}