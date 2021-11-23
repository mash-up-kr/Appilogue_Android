package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ApiResponse<T>(
    @field:Json(name = "items") val items: List<T> = emptyList(),
    @field:Json(name = "meta") val meta: Meta = Meta()
) {
    @JsonClass(generateAdapter = true)
    data class Meta(
        @field:Json(name = "totalItems") val totalItems: Int = 0,
        @field:Json(name = "itemCount") val itemCount: Int = 0,
        @field:Json(name = "itemsPerPage") val itemsPerPage: Int = 0,
        @field:Json(name = "totalPages") val totalPages: Int = 0,
        @field:Json(name = "currentPage") val currentPage: Int = 0,
    )
}
