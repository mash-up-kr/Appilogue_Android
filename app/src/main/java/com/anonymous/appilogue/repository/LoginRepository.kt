package com.anonymous.appilogue.repository

import com.anonymous.appilogue.features.retrofit.NetworkManager
import com.anonymous.appilogue.model.SendEmail
import io.reactivex.rxjava3.core.Single

class LoginRepository {
    fun sendCertificationEmail(email: Map<String, String>): Single<SendEmail> {
        return NetworkManager.AppilogueApi.sendCertificationToEmail(email)
    }
}
