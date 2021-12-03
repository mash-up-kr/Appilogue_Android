package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.*
import com.anonymous.appilogue.repository.remote.AuthApi
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class LoginRepository @Inject constructor(private val authApi: AuthApi) {
    fun sendCertificationEmail(email: Map<String, String>): Single<SendEmail> {
        return authApi.sendCertificationToEmail(email)
    }

    fun verifyCertificationNumber(postData: PostVerifyCode): Single<VerifyCode> {
        return authApi.verifyCertificationNumber(postData)
    }

    fun validateNickname(nickname: Map<String, String>): Single<ValidateNickname> {
        return authApi.validateNickname(nickname)
    }

    fun postToServerUserData(userData: SignUp): Single<SignUpResult> {
        return authApi.postToServerUserData(userData)
    }

    fun loginWithEmailPassword(loginData: Login): Single<LoginResult> {
        return authApi.loginWithEmailPassword(loginData)
    }
}
