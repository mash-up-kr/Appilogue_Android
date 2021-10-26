package com.anonymous.appilogue.features.notification

import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anonymous.appilogue.R
import com.anonymous.appilogue.model.Notification
import com.anonymous.appilogue.utils.dateToPassedTime


@BindingAdapter("notificationText")
fun TextView.setNotificationText(notification: Notification) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            val ellipsisText = context.getString(
                notification.notificationType.stringResId,
                notification.from.nickName,
                notification.date.dateToPassedTime(context),
                notification.description
            )
            val ellipsizedText = ellipsizeText(
                ellipsisText,
                width,
                textSize,
                maxLines,
                notification.date.dateToPassedTime(context)
            )
            text = SpannableString(ellipsizedText)
                .apply {
                    setSpan(
                        ForegroundColorSpan(context.getColor(R.color.purple_01)),
                        0, notification.from.nickName.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        ForegroundColorSpan(context.getColor(R.color.gray_02)),
                        ellipsizedText.length - notification.date.dateToPassedTime(context).length,
                        ellipsizedText.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            return true
        }
    })
}

private fun hasEllipsized(
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

private fun ellipsizeText(
    rawText: String,
    width: Int,
    textSize: Float,
    maxLines: Int,
    lastText: String
): String {
    val isEllipsis = hasEllipsized(rawText, width, textSize, maxLines)
    if (isEllipsis) {
        var ellipsisText = rawText
        while (hasEllipsized(ellipsisText, width, textSize, maxLines)) {
            ellipsisText = ellipsisText.substring(0, ellipsisText.length - 1).trim()
        }
        return "${
            ellipsisText.substring(
                0,
                ellipsisText.length - ELLIPSIZE_NUM - lastText.length - ADDITIONAL_TEXT_SPACE
            )
        }${".".repeat(ELLIPSIZE_NUM)} $lastText"
    } else return rawText
}

const val ELLIPSIZE_NUM = 3
const val ADDITIONAL_TEXT_SPACE = 5
