package com.anonymous.appilogue.network

import com.anonymous.appilogue.model.AppModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {

    @GET("v1/api/app/{appName}")
    suspend fun findApp(
        @Path("appName") appName: String
    ): Response<AppModel>
}