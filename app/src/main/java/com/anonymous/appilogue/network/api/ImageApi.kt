package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.dto.ImageDto
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ImageApi {

    @Multipart
    @POST("/v1/image/upload")
    suspend fun uploadImage(
        @Query("maxKB") maxKB: Int,
        @Query("format") format: String,
        @Part imageFile: MultipartBody.Part
    ): Response<ImageDto>
}