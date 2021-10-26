package com.anonymous.appilogue.model

import com.anonymous.appilogue.R
import java.util.*

data class Notification(
    val id: Int,
    val iconUrl: String,
    val description: String,
    val date: Date,
    val to: User,
    val from: User,
    val notificationType: NotificationType
) {
    enum class NotificationType(val stringResId: Int) {
        COMMENT_TO_COMMENT(R.string.notification_comment_to_comment),
        LIKE(R.string.notification_like),
        COMMENT_TO_REVIEW(R.string.notification_comment_to_review)
    }
}