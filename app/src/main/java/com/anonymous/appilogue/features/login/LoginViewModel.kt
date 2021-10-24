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
    private lateinit var job: Job

    val inputText = MutableLiveData<String>()
    val timer: LiveData<String> = _timer

    init {
        timerStart()
    }

    private fun timerStart() {
        if (::job.isInitialized) job.cancel()
        _timerCount = 600000

        job = viewModelScope.launch {
            while (_timerCount > 0) {
                val min = _timerCount / 60000
                val sec = (_timerCount % 60000) / 1000
                when (sec) {
                    in 0..9 -> {
                        _timer.value = "$min:0$sec"
                        _timerCount -= 1000
                        delay(1000L)
                    }
                    else -> {
                        _timer.value = "$min:$sec"
                        _timerCount -= 1000
                        delay(1000L)
                    }
                }
            }
        }
    }

    fun timerReset() {
        timerStart()
    }

    fun stopTimer() {
        if (::job.isInitialized) job.cancel()
    }
}
