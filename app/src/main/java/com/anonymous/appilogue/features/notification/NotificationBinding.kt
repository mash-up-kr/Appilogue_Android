package com.anonymous.appilogue.features.notification

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anonymous.appilogue.R
import com.anonymous.appilogue.model.Notification
import com.anonymous.appilogue.utils.dateToPassedTime
import com.anonymous.appilogue.utils.ellipsizeText


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
                notification.date.dateToPassedTime(context),
                ELLIPSIZE_NUM
            )
            text.toString().replace(" ", "\u00A0")
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

const val ELLIPSIZE_NUM = 3
