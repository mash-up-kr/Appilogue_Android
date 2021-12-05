package com.anonymous.appilogue.network

import com.anonymous.appilogue.model.*
import retrofit2.Response
import retrofit2.http.*

interface ReviewApi {

    @GET("v1/api/search/review")
    suspend fun searchReviews(
        @Query("hole") hole: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int = DEFAULT_LIMIT_SIZE,
    ): Response<ApiResponse<ReviewInfo>>

    @GET("v1/api/review/{reviewId}")
    suspend fun searchReview(
        @Path("reviewId") reviewId: Int
    ): Response<ReviewInfo>

    @POST("v1/api/review")
    suspend fun registerReview(
        @Body reviewDto: ReviewDto
    ): Response<Void>

    @DELETE("v1/api/review/{reviewId}")
    suspend fun removeReview(
        @Path("reviewId") reviewId: Int
    ): Response<Void>

    @POST("v1/api/review/like")
    suspend fun plusLike(
        @Body likeDto: LikeDto
    ): Response<Void>

    companion object {
        private const val DEFAULT_LIMIT_SIZE = 10
    }
}