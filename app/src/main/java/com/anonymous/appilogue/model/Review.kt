package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Review(
    @Json(name = "app") val app: App,
    @Json(name = "comments") val comments: List<Comment>,
    @Json(name = "content") val content: String,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "deletedAt") val deletedAt: String?,
    @Json(name = "hole") val hole: String,
    @Json(name = "id") val id: Int,
    @Json(name = "likes") val likes: List<Like>,
    @Json(name = "updatedAt") val updatedAt: String,
    @Json(name = "user") val user: User
)