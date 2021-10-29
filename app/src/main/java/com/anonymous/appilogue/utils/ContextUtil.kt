package com.anonymous.appilogue.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager

fun Context.showKeyboardUp() {
    val inputMethodService = getSystemService(Context.INPUT_METHOD_SERVICE)
    if (inputMethodService is InputMethodManager) {
        inputMethodService.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }
}