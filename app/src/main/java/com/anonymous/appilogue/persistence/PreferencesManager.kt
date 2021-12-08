package com.anonymous.appilogue.persistence

import android.content.Context
import android.content.SharedPreferences

object PreferencesManager {
    private const val APPILOGUE_SHARED_PREFS = "appilogue_prefs"
    private const val LOGIN_TOKEN_KEY = "appilogue_token"
    private const val USER_ID_PREFS_KEY = "appilogue_ID"
    private const val KEY_ONBOARDING = "key_onboarding"

    private lateinit var prefs: SharedPreferences

    fun initialize(context: Context) {
        prefs = context.getSharedPreferences(APPILOGUE_SHARED_PREFS, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        putString(LOGIN_TOKEN_KEY, token)
    }

    fun saveUserId(userId: Int) {
        putString(USER_ID_PREFS_KEY, userId.toString())
    }

    fun logout() {
        clear(LOGIN_TOKEN_KEY)
        clear(USER_ID_PREFS_KEY)
    }

    fun getToken(): String? = prefs.getString(LOGIN_TOKEN_KEY, null)

    fun getMyId(): Int? = prefs.getString(USER_ID_PREFS_KEY, null)?.toInt()

    fun isOnBoardingDone(): Boolean =
        prefs.getBoolean(KEY_ONBOARDING, false)

    fun finishOnBoarding() = putBoolean(KEY_ONBOARDING, true)

    private fun putString(key: String, value: String) {
        prefs.edit().apply {
            putString(key, value)
            apply()
        }
    }

    private fun putBoolean(key: String, value: Boolean) {
        prefs.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    private fun clear(key: String) {
        prefs.edit().apply {
            remove(key)
            apply()
        }
    }
}