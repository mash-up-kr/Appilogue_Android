package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.dto.SearchDto
import retrofit2.Response
import retrofit2.http.PATCH

interface UserApi {

    @PATCH("v1/api/user")
    suspend fun updateUser(
    ): Response<SearchDto>
}