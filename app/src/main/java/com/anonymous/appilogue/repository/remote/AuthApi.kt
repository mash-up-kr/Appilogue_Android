package com.anonymous.appilogue.repository.remote

import com.anonymous.appilogue.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/send-email")
    fun sendCertificationToEmail(@Body email: Map<String, String>): Single<SendEmail>

    @POST("auth/verify-code")
    fun verifyCertificationNumber(@Body verifyData: PostVerifyCode): Single<VerifyCode>

    @POST("auth/validate-nickname")
    fun validateNickname(@Body nickname: Map<String, String>): Single<ValidateNickname>

    @POST("auth/signup")
    fun postToServerUserData(@Body userData: SignUp): Single<SignUpResult>
}
