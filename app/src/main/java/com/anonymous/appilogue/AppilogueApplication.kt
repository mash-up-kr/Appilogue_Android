package com.anonymous.appilogue

import android.app.Application
import com.anonymous.appilogue.persistence.PreferencesManager
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.facebook.stetho.Stetho
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppilogueApplication : Application() {

    override fun onCreate() {
        prefs = AppilogueSharedPreferences(applicationContext)
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(AnonymousDebugTree())
        }
        Stetho.initializeWithDefaults(this)
        KakaoSdk.init(this, NATIVE_APP_KEY)

        PreferencesManager.initialize(this)
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            val client: FlipperClient = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.start()
        }
    }

    private class AnonymousDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String =
            "${element.fileName}:${element.lineNumber}:${element.methodName}"
    }

    companion object {
        lateinit var prefs: AppilogueSharedPreferences
        private const val NATIVE_APP_KEY = "65f916a19cd409f4ce358c882e082508"
    }

}
