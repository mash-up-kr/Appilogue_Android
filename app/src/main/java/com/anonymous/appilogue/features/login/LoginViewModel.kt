package com.anonymous.appilogue.features.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.model.*
import com.anonymous.appilogue.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        _timerCount = INITIAL_TIME

        job = viewModelScope.launch {
            while (_timerCount >= 0) {
                val min = _timerCount / ONE_MIN
                val sec = (_timerCount % ONE_MIN)
                _timer.value = String.format("%02d:%02d", min, sec)
                _timerCount -= ONE_SEC
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

    fun sendCertificationNumber(duplicateCheck: Boolean): Single<SendEmailResult> {
        return loginRepository.sendCertificationEmail(SendEmail(duplicateCheck, emailAddress.value.toString()))
    }

    fun verifyCertificationNumber(): Single<VerifyCode> {
        return loginRepository.verifyCertificationNumber(
            PostVerifyCode(emailAddress.value.toString(), certificationNumber.value.toString())
        )
    }

    fun validateNickname(nickname: String): Single<ValidateNickname> {
        return loginRepository.validateNickname(mapOf("nickname" to nickname))
    }

    fun sendToServerUserData(): Single<SignUpResult> {
        return loginRepository.postToServerUserData(
            SignUp(
                email = emailAddress.value.toString(),
                nickname = nickName.value.toString(),
                password = checkPassword.value.toString(),
            )
        ).subscribeOn(Schedulers.io())
    }

    fun loginWithEmailPassword(): Single<LoginResult> {
        return loginRepository.loginWithEmailPassword(Login(emailAddress.value.toString(), password.value.toString()))
    }

    fun updatePassword(): Single<UpdatePasswordResult> {
        return loginRepository.updatePassword(UpdatePassword(emailAddress.value.toString(), checkPassword.value.toString()))
    }

    companion object {
        const val INITIAL_TIME = 600
        const val ONE_MIN = 60
        const val ONE_SEC = 1
    }
}
