package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpaceDustItem(
    @Json(name = "dressed_url_android")
    val dressedUrlAndroid: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "item_url_android")
    val itemUrlAndroid: String,
)