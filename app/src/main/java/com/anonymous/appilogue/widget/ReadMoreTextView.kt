package com.anonymous.appilogue.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import com.anonymous.appilogue.R

class ReadMoreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private val globalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener by lazy {
        ViewTreeObserver.OnGlobalLayoutListener {
            handleOnGlobalLayout()
        }
    }

    init {
        viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)
    }

    private fun handleOnGlobalLayout() {
        viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)

        addReadMoreText()
    }

    @SuppressLint("SetTextI18n")
    private fun addReadMoreText() {
        val ellipsisCount = layout?.getEllipsisCount(maxLines - 1) ?: return
        val isEllipsis = ellipsisCount > 0
        val readMoreText = context.resources.getString(R.string.read_more_text)

        if (isEllipsis) {
            val ellipsisIndex = getEllipsisIndex(readMoreText)
            val maxTextIndex = layout.getLineVisibleEnd(maxLines - 1)
            val originalSubText = text.substring(0, (maxTextIndex - 1) - ellipsisIndex)

            val collapseText = buildSpannedString {
                append(originalSubText)
                color(ContextCompat.getColor(context, R.color.gray_02)) {
                    append(readMoreText)
                }
            }

            text = collapseText
        }
    }

    private fun getEllipsisIndex(readMoreText: String): Int {
        val lastLineStartIndex = layout.getLineVisibleEnd(maxLines - 2) + 1
        val lastLineEndIndex = layout.getLineVisibleEnd(maxLines - 1)
        val lastLineText = text.substring(lastLineStartIndex, lastLineEndIndex)

        val bounds = Rect()
        paint.getTextBounds(lastLineText, 0, lastLineText.length, bounds)

        var ellipsisIndex = -1
        do {
            ellipsisIndex++
            val subText = lastLineText.substring(0, lastLineText.length - ellipsisIndex)
            val replacedText = subText + readMoreText
            paint.getTextBounds(replacedText, 0, replacedText.length, bounds)
            val replacedTextWidth = bounds.width()
        } while (replacedTextWidth > width)

        return ellipsisIndex
    }
}