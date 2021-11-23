package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppModel(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "name") val name: String?,
    @field:Json(name = "iconUrl") val iconUrl: String?
)