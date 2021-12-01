package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SignUpResult(
    @Json(name = "avatarItemType")
    val avatarItemType: String?,
    @Json(name = "email")
    val email: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "isVerified")
    val isVerified: Boolean,
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "planetType")
    val planetType: String?,
    @Json(name = "profileImage")
    val profileImage: Any?
)
