package com.anonymous.appilogue.model.dto


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateUserDto(
    @Json(name = "avatarItemType")
    val avatarItemType: String?,
    @Json(name = "nickname")
    val nickname: String,
    @Json(name = "planetType")
    val planetType: String?,
    @Json(name = "profileImage")
    val profileImage: String
)