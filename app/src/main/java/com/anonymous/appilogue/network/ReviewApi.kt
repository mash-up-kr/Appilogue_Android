package com.anonymous.appilogue.network

import com.anonymous.appilogue.model.ApiResponse
import com.anonymous.appilogue.model.ReviewInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReviewApi {

    @GET("v1/api/search/review")
    suspend fun searchReviews(
        @Query("hole") hole: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = DEFAULT_LIMIT_SIZE,
    ): Response<ApiResponse<ReviewInfo>>

    @GET("v1/api/search/review")
    suspend fun searchReviewsFromUser(
        @Query("userId") userId: String,
        @Query("hole") hole: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = DEFAULT_LIMIT_SIZE,
    ): Response<ApiResponse<ReviewInfo>>

    companion object {
        private const val DEFAULT_LIMIT_SIZE = 10
    }
}