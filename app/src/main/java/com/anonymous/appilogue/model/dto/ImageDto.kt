package com.anonymous.appilogue.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto(
    @field:Json(name = "url") val url: String = ""
)
