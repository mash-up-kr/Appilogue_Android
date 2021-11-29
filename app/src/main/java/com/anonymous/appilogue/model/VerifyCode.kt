package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VerifyCode(
    @Json(name = "email")
    val email: String,
    @Json(name = "isCodeExpired")
    val isCodeExpired: Boolean?,
    @Json(name = "isVerify")
    val isVerify: Boolean
)
