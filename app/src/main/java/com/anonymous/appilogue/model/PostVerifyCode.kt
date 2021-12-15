package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostVerifyCode(
    @field:Json(name = "email") val email: String,
    @field:Json(name = "code") val code: String,
)
