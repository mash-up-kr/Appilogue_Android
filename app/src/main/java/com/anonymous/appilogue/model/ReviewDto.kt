package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewDto(
    @field:Json(name = "hole") val hole: String,
    @field:Json(name = "content") val content: String,
    @field:Json(name = "appName") val appName: String,
    @field:Json(name = "appIconUrl") val appIconUrl: String,
    @field:Json(name = "hashtags") val hashtags: List<String>,
)
