package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.dto.SearchDto
import com.anonymous.appilogue.model.ReviewModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("v1/api/search/review")
    suspend fun searchReviews(
        @Query("userId") userId: Int,
        @Query("page") page: Int,
        @Query("limit") limit: Int = DEFAULT_LIMIT_SIZE
    ): Response<SearchDto<ReviewModel>>

    @GET("v1/api/search/review")
    suspend fun searchReviews(
        @Query("userId") userId: Int,
        @Query("hole") hole: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = DEFAULT_LIMIT_SIZE
    ): Response<SearchDto<ReviewModel>>

    @GET("v1/api/search/review")
    suspend fun searchReviews(
        @Query("hole") hole: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = DEFAULT_LIMIT_SIZE
    ): Response<SearchDto<ReviewModel>>

    companion object {
        private const val DEFAULT_LIMIT_SIZE = 10
    }
}