package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.User
import com.anonymous.appilogue.model.dto.UpdateUserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH

interface UserApi {

    @PATCH("v1/api/user")
    suspend fun updateUser(
        @Body updateUserDto: UpdateUserDto
    ): Response<User>
}