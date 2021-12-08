package com.anonymous.appilogue.widget

import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View

class ReadMoreSpannable(
    private val color: Int
) : ClickableSpan() {
    override fun updateDrawState(ds: TextPaint) {
        ds.isUnderlineText = true
        ds.color = color
    }

    override fun onClick(widget: View) {
        // do nothing
    }
}