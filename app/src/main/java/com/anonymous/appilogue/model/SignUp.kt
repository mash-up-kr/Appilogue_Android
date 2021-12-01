package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUp(
    @Json(name = "avatarTypeName")
    val avatarTypeName: String? = null,
    @Json(name = "email")
    val email: String,
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "planetType")
    val planetType: String? = null
)
