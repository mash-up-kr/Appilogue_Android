package com.anonymous.appilogue.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AppModel(
    @field:Json(name = "id") val id: Int = 0,
    @field:Json(name = "name") val name: String? = null,
    @field:Json(name = "iconUrl") val iconUrl: String? = null,
    @field:Json(name = "review_count_black") val reviewCountBlack: Int = 0,
    @field:Json(name = "review_count_white") val reviewCountWhite: Int = 0,
) : Parcelable