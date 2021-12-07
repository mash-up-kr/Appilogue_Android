package com.anonymous.appilogue.repository.remote

import com.anonymous.appilogue.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/send-email")
    fun sendCertificationToEmail(@Body email: SendEmail): Single<SendEmailResult>

    @POST("auth/verify-code")
    fun verifyCertificationNumber(@Body verifyData: PostVerifyCode): Single<VerifyCode>

    @POST("auth/validate-nickname")
    fun validateNickname(@Body nickname: Map<String, String>): Single<ValidateNickname>

    @POST("auth/signup")
    fun postToServerUserData(@Body userData: SignUp): Single<SignUpResult>

    @POST("auth/login")
    fun loginWithEmailPassword(@Body loginData: Login): Single<LoginResult>

    @PATCH("auth/update-password")
    fun updatePassword(@Body updatePassword: UpdatePassword): Single<UpdatePasswordResult>
}
