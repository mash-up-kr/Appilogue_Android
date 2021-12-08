package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @GET("v1/api/auth/me")
    suspend fun fetchMyInformation(): Response<User>

    @POST("v1/api/auth/send-email")
    fun sendCertificationToEmail(@Body emailModel: SendEmailModel): Single<SendEmailResult>

    @POST("v1/api/auth/verify-code")
    fun verifyCertificationNumber(@Body verifyData: PostVerifyCode): Single<VerifyCode>

    @POST("v1/api/auth/validate-nickname")
    fun validateNickname(@Body nickname: Map<String, String>): Single<ValidateNickname>

    @POST("v1/api/auth/signup")
    fun postToServerUserData(@Body userData: SignUpModel): Single<SignUpResult>

    @POST("v1/api/auth/login")
    fun loginWithEmailPassword(@Body loginModelData: LoginModel): Single<TokenModel>

    @PATCH("v1/api/auth/update-password")
    fun updatePassword(@Body updatePasswordModel: UpdatePasswordModel): Single<UpdatePasswordResult>
}
