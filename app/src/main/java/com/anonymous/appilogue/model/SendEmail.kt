package com.anonymous.appilogue.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SendEmail(
    @Json(name = "isSend")
    val isSend: Boolean,
    @Json(name = "isUserExist")
    val isUserExist: Boolean? = null
)
