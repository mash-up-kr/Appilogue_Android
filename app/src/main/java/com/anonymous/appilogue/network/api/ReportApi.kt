package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.ReportModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ReportApi {

    @POST("v1/api/abuse-report")
    suspend fun reportAbuse(
        @Body reportModel: ReportModel
    ): Response<Void>
}