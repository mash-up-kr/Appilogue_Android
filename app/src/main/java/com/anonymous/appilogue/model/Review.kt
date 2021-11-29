package com.anonymous.appilogue.model

data class Review(
    val app: App,
    val comments: List<Comment>,
    val content: String,
    val createdAt: String,
    val deletedAt: Any,
    val hole: String,
    val id: Int,
    val likes: List<Like>,
    val updatedAt: String,
    val user: User
)