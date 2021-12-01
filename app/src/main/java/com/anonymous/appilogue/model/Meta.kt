package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "currentPage") val currentPage: Int,
    @Json(name = "itemCount") val itemCount: Int,
    @Json(name = "itemsPerPage") val itemsPerPage: Int,
    @Json(name = "totalItems") val totalItems: Int,
    @Json(name = "totalPages") val totalPages: Int

)