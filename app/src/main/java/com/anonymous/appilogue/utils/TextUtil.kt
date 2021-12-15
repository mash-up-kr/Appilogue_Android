package com.anonymous.appilogue.utils

import android.graphics.Paint

fun hasEllipsized(
    text: String,
    tvWidth: Int,
    textSize: Float,
    maxLines: Int
): Boolean {
    val paint = Paint()
    paint.textSize = textSize
    val size = paint.measureText(text).toInt()
    return tvWidth * maxLines < size
}

fun ellipsizeText(
    rawText: String,
    width: Int,
    textSize: Float,
    maxLines: Int,
    lastText: String,
    ellipsizeNum: Int
): String {
    val isEllipsis = hasEllipsized(rawText, width, textSize, maxLines)
    return if (isEllipsis) {
        var ellipsisText = rawText.replace(" ", "\u00A0")
        while (hasEllipsized(ellipsisText, width, textSize, maxLines)) {
            ellipsisText = ellipsisText.substring(0, ellipsisText.length - 1).trim()
        }
        "${
            ellipsisText.substring(
                0,
                ellipsisText.length - ellipsizeNum - lastText.length - ADDITIONAL_TEXT_SPACE
            )
        }${".".repeat(ellipsizeNum)} $lastText"
    } else rawText.replace(" ", "\u00A0")
}

const val ADDITIONAL_TEXT_SPACE = 5