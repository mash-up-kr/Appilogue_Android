package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdatePassword(
    @Json(name = "email")
    val email: String,
    @Json(name = "newPassword")
    val newPassword: String
)
