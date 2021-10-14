package com.anonymous.appilogue.repository

import android.content.Context
import android.content.pm.PackageManager
import com.anonymous.appilogue.model.InstalledApp
import timber.log.Timber
import javax.inject.Inject

class SearchAppRepository @Inject constructor(
    private val applicationContext: Context
) {

    fun searchAppList(): List<InstalledApp> {
        val packageManager = applicationContext.packageManager
        val appList = packageManager.getInstalledApplications(
            PackageManager.MATCH_DISABLED_COMPONENTS)

        return appList
            .filter { appInfo -> !appInfo.name.isNullOrBlank() && packageManager.getApplicationLabel(appInfo).isNotBlank() }
            .map { appInfo ->
                val appName = packageManager.getApplicationLabel(appInfo).toString()
                Timber.d(appName)
                InstalledApp(appName, packageManager.getApplicationIcon(appInfo))
            }
    }
}