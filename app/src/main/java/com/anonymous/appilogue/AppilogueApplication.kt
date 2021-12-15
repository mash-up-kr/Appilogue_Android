package com.anonymous.appilogue

import android.app.Application
import com.anonymous.appilogue.persistence.PreferencesManager
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppilogueApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(AnonymousDebugTree())
        }
        KakaoSdk.init(this, NATIVE_APP_KEY)

        PreferencesManager.initialize(this)
    }

    private class AnonymousDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String =
            "${element.fileName}:${element.lineNumber}:${element.methodName}"
    }

    companion object {
        private const val NATIVE_APP_KEY = "65f916a19cd409f4ce358c882e082508"
    }

}
