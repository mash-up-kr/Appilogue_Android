package com.anonymous.appilogue

import android.app.Application
import com.anonymous.appilogue.features.search.AppSearchManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AppilogueApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    @Inject lateinit var appSearchManager: AppSearchManager

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(AnonymousDebugTree())
        }

        updateInstalledAppList()
    }

    private fun updateInstalledAppList() {
        applicationScope.launch(Dispatchers.IO) {
            appSearchManager.updateInstalledAppList()
        }
    }

    private class AnonymousDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String =
            "${element.fileName}:${element.lineNumber}:${element.methodName}"
    }
}