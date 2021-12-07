package com.anonymous.appilogue.model.dto

import com.anonymous.appilogue.model.Meta
import com.anonymous.appilogue.model.Review
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchDto(
    @Json(name = "items") val reviews: List<Review>,
    @Json(name = "meta") val meta: Meta
)