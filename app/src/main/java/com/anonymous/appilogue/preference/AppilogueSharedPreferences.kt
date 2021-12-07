package com.anonymous.appilogue.preference

interface AppilogueSharedPreferences {
    fun getOnboardingIsDone(): Boolean
    fun finishOnboarding()
    fun saveAccessToken(token: String?)
    fun getAccessToken(): String?
}