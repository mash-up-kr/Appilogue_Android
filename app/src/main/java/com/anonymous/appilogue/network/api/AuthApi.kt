package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.SpaceDustItem
import com.anonymous.appilogue.model.dto.MyInformationDto
import com.anonymous.appilogue.model.dto.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Url

interface AuthApi {

    @GET("v1/api/auth/me")
    suspend fun fetchMyInformation(): Response<MyInformationDto>
}