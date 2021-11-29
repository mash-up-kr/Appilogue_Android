package com.anonymous.appilogue.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.model.PostVerifyCode
import com.anonymous.appilogue.model.VerifyCode
import com.anonymous.appilogue.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    private var _timerCount = 0
    private val _timer = MutableLiveData<String>()
    private var job: Job? = null

    var lostPassword = false
    var certificationNumber = MutableLiveData<String>()
    val emailAddress = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val checkPassword = MutableLiveData<String>()
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

    fun sendCertificationNumber() {
        loginRepository.sendCertificationEmail(mapOf("email" to emailAddress.value.toString()))
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.isSend) {
                    Timber.d("sendCertificationNumber: $it.isUserExist ${emailAddress.value.toString()}으로 이메일 전송 선공")
                }
            }, {
                Timber.d("${it.message} error!!!")
            })
    }

    fun resendCertificationNumber() {
        sendCertificationNumber()
    }

    fun verifyCertificationNumber(): Single<VerifyCode> {
        return loginRepository.verifyCertificationNumber(
            PostVerifyCode(emailAddress.value.toString(), certificationNumber.value.toString())
        )
    }
}
