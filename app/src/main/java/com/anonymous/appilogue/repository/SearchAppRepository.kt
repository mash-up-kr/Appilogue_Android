package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.model.toData
import com.anonymous.appilogue.model.toEntity
import com.anonymous.appilogue.persistence.InstalledAppDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchAppRepository @Inject constructor(
    private val installedAppDao: InstalledAppDao
) {

    suspend fun insertInstalledAppList(appList: List<InstalledApp>) {
        installedAppDao.insertInstalledAppList(appList.map { it.toEntity() })
    }

    fun fetchInstalledAppList(): Flow<List<InstalledApp>> {
        return installedAppDao.fetchInstalledAppList().map { appList ->
            appList.map { it.toData() }
        }
    }
}