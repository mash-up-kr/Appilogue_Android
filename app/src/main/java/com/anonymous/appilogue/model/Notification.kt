package com.anonymous.appilogue.model

import java.util.*

data class Notification(
    val id: Int,
    val iconUrl: String,
    val description: String,
    val date: Date
)