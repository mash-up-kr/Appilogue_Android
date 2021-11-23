package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommentModel(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "content") val content: String? = "",
    @field:Json(name = "createdAt") val createdAt: String?,
    @field:Json(name = "updatedAt") val updatedAt: String?,
    @field:Json(name = "deletedAt") val deletedAt: String?,
    @field:Json(name = "parentId") val parentId: Int? = 0
)
