package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewInfo(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "content") val content: String?,
    @field:Json(name = "hole") val hole: String,
    @field:Json(name = "user") val user: User,
    @field:Json(name = "createdAt") val createdAt: String?,
    @field:Json(name = "updatedAt") val updatedAt: String?,
    @field:Json(name = "deletedAt") val deletedAt: String?,
    @field:Json(name = "app") val app: AppModel,
    @field:Json(name = "likes") val likes: List<LikesModel> = emptyList(),
    @field:Json(name = "comments") val comments: List<CommentModel> = emptyList()
)
