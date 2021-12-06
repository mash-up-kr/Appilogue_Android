package com.anonymous.appilogue.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

fun View.showKeyboardUp() {
    val inputMethodService = context.getSystemService(Context.INPUT_METHOD_SERVICE)
    if (inputMethodService is InputMethodManager) {
        inputMethodService.showSoftInput(this, 0)
    }
}

fun View.hideKeyboardDown() {
    val inputMethodService = context.getSystemService(Context.INPUT_METHOD_SERVICE)
    if (inputMethodService is InputMethodManager) {
        inputMethodService.hideSoftInputFromWindow(windowToken, 0)
    }
}

fun TextView.handleSelectEvent(
    contentTextId: Int,
    holeTextId: Int,
    spanColorId: Int
) {
    handleSelectEvent(context.getString(contentTextId), holeTextId, spanColorId)
}

fun TextView.handleSelectEvent(
    content: String,
    holeTextId: Int,
    spanColorId: Int
) {
    visibility = View.VISIBLE

    val holeText = context.getString(holeTextId)
    val spanColor = context.getColor(spanColorId)
    val span = SpannableString(content)
    span.setSpan(
        ForegroundColorSpan(spanColor),
        content.indexOf(holeText),
        content.indexOf(holeText) + holeText.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = span
}