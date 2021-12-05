package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HashTagModel(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "name") val name: String,
)
