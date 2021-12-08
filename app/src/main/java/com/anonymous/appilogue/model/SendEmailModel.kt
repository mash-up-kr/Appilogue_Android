package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendEmailModel(
    @Json(name = "allowEmailDuplicate")
    val allowEmailDuplicate: Boolean,
    @Json(name = "email")
    val email: String
)
