package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "nickname") val nickname: String = "",
    @Json(name = "profileImage") val profileImage: String = "",
    @Json(name = "email") val email: String? = "",
    @Json(name = "planetType") val planetType: String? = "",
)