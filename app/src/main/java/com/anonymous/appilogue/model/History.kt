package com.anonymous.appilogue.model

import com.anonymous.appilogue.R
import java.util.*

data class History(
    val id: Int,
    val iconUrl: String,
    val description: String,
    val date: Date,
    val to: User,
    val from: User,
    val historyType: HistoryType
) {
    enum class HistoryType(val stringResId: Int) {
        COMMENT_TO_COMMENT(R.string.history_comment_to_comment),
        LIKE(R.string.history_like),
        COMMENT_TO_REVIEW(R.string.history_comment_to_review)
    }
}