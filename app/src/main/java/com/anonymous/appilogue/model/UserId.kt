package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserId(
    @Json(name = "id") val id: Int
)