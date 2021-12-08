package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReportModel(
    @field:Json(name = "type") val type: String,
    @field:Json(name = "targetId") val targetId: Int
)
