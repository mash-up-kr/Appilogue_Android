package com.anonymous.appilogue.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText.showSoftInput(): Boolean {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: return false
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    return inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}