package com.anonymous.coffin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AnonymousApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(AnonymousDebugTree())
        }
    }

    private class AnonymousDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String =
            "${element.fileName}:${element.lineNumber}:${element.methodName}"
    }
}