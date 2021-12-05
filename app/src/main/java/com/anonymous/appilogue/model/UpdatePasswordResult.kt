package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdatePasswordResult(
    @Json(name = "isUpdated")
    val isUpdated: Boolean
)
