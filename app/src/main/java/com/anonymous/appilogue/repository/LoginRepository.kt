package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.PostVerifyCode
import com.anonymous.appilogue.model.SendEmail
import com.anonymous.appilogue.model.VerifyCode
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
}
