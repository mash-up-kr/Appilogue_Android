package com.anonymous.appilogue.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

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