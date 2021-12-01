package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class App(
    @Json(name = "iconUrl") val iconUrl: String,
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String
)