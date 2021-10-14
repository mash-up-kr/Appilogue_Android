package com.anonymous.appilogue

import android.app.Application
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppilogueApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(AnonymousDebugTree())
        }
        Stetho.initializeWithDefaults(this)
    }

    private class AnonymousDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String =
            "${element.fileName}:${element.lineNumber}:${element.methodName}"
    }
}