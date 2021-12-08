package com.anonymous.appilogue.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun fetchMyInformation(
        onLoginSuccess: () -> Unit,
        onLoginFailure: () -> Unit
    ) {
        viewModelScope.launch {
            userRepository.fetchMyInformation().let {
                delay(2000L)
                if (it != null) {
                    onLoginSuccess()
                } else {
                    onLoginFailure()
                }
            }
        }
    }

}