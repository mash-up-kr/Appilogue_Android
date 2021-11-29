package com.anonymous.appilogue.repository

import com.anonymous.appilogue.features.retrofit.NetworkManager
import com.anonymous.appilogue.model.PostVerifyCode
import com.anonymous.appilogue.model.SendEmail
import com.anonymous.appilogue.model.VerifyCode
import io.reactivex.rxjava3.core.Single

class LoginRepository {
    fun sendCertificationEmail(email: Map<String, String>): Single<SendEmail> {
        return NetworkManager.AppilogueApi.sendCertificationToEmail(email)
    }

    fun verifyCertificationNumber(postData: PostVerifyCode): Single<VerifyCode> {
        return NetworkManager.AppilogueApi.verifyCertificationNumber(postData)
    }
}
