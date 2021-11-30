package com.anonymous.appilogue.preference

import android.content.SharedPreferences
import javax.inject.Inject

class DefaultAppilogueSharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) :
    AppilogueSharedPreferences {

    override fun getOnboardingIsDone(): Boolean =
        sharedPreferences.getBoolean(KEY_ONBOARDING, false)

    override fun finishOnboarding() {
        sharedPreferences.edit().apply {
            putBoolean(KEY_ONBOARDING, true)
            apply()
        }
    }

    override fun saveAccessToken(token: String?) {
        sharedPreferences.edit().apply {
            putString(KEY_ACCESS_TOKEN, token)
            apply()
        }
    }

    override fun getAccessToken(): String? = sharedPreferences.getString(KEY_ACCESS_TOKEN, null)

    companion object {
        private const val KEY_ONBOARDING = "key_onboarding"
        private const val KEY_ACCESS_TOKEN = "key_access_token"
    }
}