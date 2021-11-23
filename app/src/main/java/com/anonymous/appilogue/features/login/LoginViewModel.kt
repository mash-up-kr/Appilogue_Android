package com.anonymous.appilogue.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private var _timerCount = 0
    private val _timer = MutableLiveData<String>()
    private var job: Job? = null

    var lostPassword = false
    val password = MutableLiveData<String>()
    val checkPassword = MutableLiveData<String>()
    val emailAddress = MutableLiveData<String>()
    val nickName = MutableLiveData<String>()
    val timer: LiveData<String> = _timer

    fun timerStart() {
        _timerCount = 600

        job = viewModelScope.launch {
            while (_timerCount >= 0) {
                val min = _timerCount / 60
                val sec = (_timerCount % 60)
                _timer.value = String.format("%02d:%02d", min, sec)
                _timerCount -= 1
                delay(1000L)
            }
            stopTimer()
        }
    }

    fun timerReset() {
        timerStart()
    }

    fun stopTimer() {
        job?.cancel()
    }
}
