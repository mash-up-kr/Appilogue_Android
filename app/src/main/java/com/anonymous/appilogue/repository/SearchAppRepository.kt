package com.anonymous.appilogue.repository

import com.anonymous.appilogue.exceptions.EmptyResponseException
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.AppModel
import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.model.toData
import com.anonymous.appilogue.model.toEntity
import com.anonymous.appilogue.network.api.AppApi
import com.anonymous.appilogue.persistence.InstalledAppDao
import timber.log.Timber
import javax.inject.Inject

class SearchAppRepository @Inject constructor(
    private val installedAppDao: InstalledAppDao,
    private val appApi: AppApi
) : Repository {

    suspend fun insertInstalledAppList(appList: List<InstalledApp>) {
        val result = installedAppDao.insertInstalledAppList(appList.map { it.toEntity() })
        Timber.d(result.toString())
    }

    suspend fun fetchInstalledAppList(): List<InstalledApp> {
        return installedAppDao.fetchInstalledAppList().map { it.toData() }
    }

    suspend fun findAppInfo(appName: String): UiState<AppModel> {
        return try {
            val response = appApi.findApp(appName)

            if (response.isSuccessful) {
                val appInfo = response.body() ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                UiState.Success(appInfo)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }
}