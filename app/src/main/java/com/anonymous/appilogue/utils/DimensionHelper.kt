package com.anonymous.appilogue.utils

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.displayMetrics
    ).toInt()
}

fun pxToDp(context: Context, px: Int): Int {
    return (px / context.resources.displayMetrics.density).toInt()
}