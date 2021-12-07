package com.anonymous.appilogue.features.search

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.graphics.drawable.toBitmap
import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.repository.SearchAppRepository
import timber.log.Timber
import javax.inject.Inject

class AppSearchManager @Inject constructor(
    private val context: Context,
    private val searchAppRepository: SearchAppRepository
) {

    suspend fun updateInstalledAppList() {
        val installedAppList = searchAppList()
        searchAppRepository.insertInstalledAppList(installedAppList)
    }

    private fun searchAppList(): List<InstalledApp> {
        val packageManager = context.packageManager
        val appList = packageManager.getInstalledApplications(
            PackageManager.MATCH_DISABLED_COMPONENTS)

        return emptyList()
//        appList
//            .filter { appInfo -> !appInfo.name.isNullOrBlank() && packageManager.getApplicationLabel(appInfo).isNotBlank() }
//            .map { appInfo ->
//                val appName = packageManager.getApplicationLabel(appInfo).toString()
//                InstalledApp(appName, packageManager.getApplicationIcon(appInfo).toBitmap())
//            }
    }
}