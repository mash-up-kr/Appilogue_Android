package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.User
import retrofit2.Response
import retrofit2.http.GET

interface AuthApi {

    @GET("v1/api/auth/me")
    suspend fun fetchMyInformation(): Response<User>
}