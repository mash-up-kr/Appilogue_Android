package com.anonymous.appilogue.repository.remote

import com.anonymous.appilogue.model.SendEmail
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("auth/send-email")
    fun sendCertificationToEmail(@Body email: Map<String, String>): Single<SendEmail>
}
