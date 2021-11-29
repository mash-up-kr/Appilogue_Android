package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.SearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("v1/api/search/review")
    suspend fun fetchReview(
        @Query("userId") userId: Int,
        @Query("hole") hole: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<List<SearchDto>>
}