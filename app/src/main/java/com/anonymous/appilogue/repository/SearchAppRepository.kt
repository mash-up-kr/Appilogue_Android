package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.model.toData
import com.anonymous.appilogue.model.toEntity
import com.anonymous.appilogue.persistence.InstalledAppDao
import timber.log.Timber
import javax.inject.Inject

class SearchAppRepository @Inject constructor(
    private val installedAppDao: InstalledAppDao
) : Repository {

    suspend fun insertInstalledAppList(appList: List<InstalledApp>) {
        val result = installedAppDao.insertInstalledAppList(appList.map { it.toEntity() })
        Timber.d(result.toString())
    }

    suspend fun fetchInstalledAppList(): List<InstalledApp> {
        return installedAppDao.fetchInstalledAppList().map { it.toData() }
    }
}