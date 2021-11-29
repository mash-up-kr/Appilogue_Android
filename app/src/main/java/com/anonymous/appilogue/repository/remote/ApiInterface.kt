package com.anonymous.appilogue.repository.remote

import com.anonymous.appilogue.model.PostVerifyCode
import com.anonymous.appilogue.model.SendEmail
import com.anonymous.appilogue.model.VerifyCode
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("auth/send-email")
    fun sendCertificationToEmail(@Body email: Map<String, String>): Single<SendEmail>

    @POST("auth/verify-code")
    fun verifyCertificationNumber(@Body verifyData: PostVerifyCode): Single<VerifyCode>
}
