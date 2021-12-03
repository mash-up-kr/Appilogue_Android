package com.anonymous.appilogue

import android.content.Context
import android.content.SharedPreferences

class AppilogueSharedPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var myEditText: String
        get() = prefs.getString(PREF_KEY_MY_EDITTEXT, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_MY_EDITTEXT, value).apply()

    companion object {
        private const val PREFS_FILENAME = "prefs"
        private const val PREF_KEY_MY_EDITTEXT = "myEditText"
    }
}
