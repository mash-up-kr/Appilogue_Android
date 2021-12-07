package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ValidateNickname(
    @Json(name = "isUnique")
    val isUnique: Boolean,
    @Json(name = "nickname")
    val nickname: String
)
