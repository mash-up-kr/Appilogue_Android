package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class CommentModel(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "content") val content: String? = "",
    @field:Json(name = "createdAt") val createdAt: Date? = Date(System.currentTimeMillis()),
    @field:Json(name = "updatedAt") val updatedAt: Date? = Date(System.currentTimeMillis()),
    @field:Json(name = "deletedAt") val deletedAt: Date? = null,
    @field:Json(name = "user") val user: User = User(),
    @field:Json(name = "parentId") val parentId: Int? = null,
    @field:Json(name = "children") val children: List<CommentModel> = emptyList(),
    @field:Json(name = "childrenCount") val childrenCount: Int = 0
)
