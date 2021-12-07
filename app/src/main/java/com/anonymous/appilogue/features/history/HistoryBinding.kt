package com.anonymous.appilogue.features.history

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.anonymous.appilogue.R
import com.anonymous.appilogue.model.History
import com.anonymous.appilogue.utils.dateToPassedTime
import com.anonymous.appilogue.utils.ellipsizeText


@BindingAdapter("historyText")
fun TextView.setHistoryText(history: History) {
    viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            val ellipsisText = context.getString(
                history.historyType.stringResId,
                history.from.nickname,
                history.date.dateToPassedTime(context),
                history.description
            )
            val ellipsizedText = ellipsizeText(
                ellipsisText,
                width,
                textSize,
                maxLines,
                history.date.dateToPassedTime(context),
                ELLIPSIZE_NUM
            )
            text.toString().replace(" ", "\u00A0")
            text = SpannableString(ellipsizedText)
                .apply {
                    setSpan(
                        ForegroundColorSpan(context.getColor(R.color.purple_01)),
                        0, history.from.nickname.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    setSpan(
                        ForegroundColorSpan(context.getColor(R.color.gray_02)),
                        ellipsizedText.length - history.date.dateToPassedTime(context).length,
                        ellipsizedText.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            return true
        }
    })
}

const val ELLIPSIZE_NUM = 3
