package com.anonymous.appilogue.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SelectableSpaceDustItem(
    val spaceDustItem: SpaceDustItem,
    val isSelected: Boolean = false
)