package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.dto.CommentDto
import com.anonymous.appilogue.model.CommentModel
import retrofit2.Response
import retrofit2.http.*

interface CommentApi {

    @GET("/v1/api/comments/{reviewId}")
    suspend fun fetchComments(
        @Path("reviewId") reviewId: Int
    ): Response<List<CommentModel>>

    @POST("/v1/api/comments")
    suspend fun registerComment(
        @Body commentDto: CommentDto
    ): Response<Void>

    @DELETE("/v1/api/comments/{commentId}")
    suspend fun removeComment(
        @Path("commentId") commentId: Int
    ): Response<Void>
}