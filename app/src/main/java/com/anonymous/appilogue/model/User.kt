package com.anonymous.appilogue.model

import com.anonymous.appilogue.features.home.EMPTY_STRING
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: Int,
    @Json(name = "nickname") val nickname: String,
    @Json(name = "profileImage") val profileImage: String,
    @Json(name = "email") val email: String? = EMPTY_STRING,
    @Json(name = "planetType") val planetType: String? = EMPTY_STRING,
)