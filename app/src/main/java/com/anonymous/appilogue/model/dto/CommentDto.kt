package com.anonymous.appilogue.model.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentDto(
    @field:Json(name = "reviewId") val reviewId: Int,
    @field:Json(name = "parentId") val parentId: Int?,
    @field:Json(name = "content") val content: String
)
