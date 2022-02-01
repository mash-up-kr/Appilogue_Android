package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class ReviewModel(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "content") val content: String? = null,
    @field:Json(name = "hole") val hole: String? = null,
    @field:Json(name = "user") val user: User = User(),
    @field:Json(name = "createdAt") val createdAt: Date? = Date(System.currentTimeMillis()),
    @field:Json(name = "updatedAt") val updatedAt: Date? = Date(System.currentTimeMillis()),
    @field:Json(name = "deletedAt") val deletedAt: Date? = null,
    @field:Json(name = "app") val app: AppModel = AppModel(),
    @field:Json(name = "hashtags") val hashtags: List<HashTagModel> = emptyList(),
    @field:Json(name = "likes") var likes: List<LikesModel> = emptyList(),
    @field:Json(name = "comments") val comments: List<CommentModel> = emptyList()
) {
    var isInvalid: Boolean = false
}
