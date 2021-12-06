package com.anonymous.appilogue.repository

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.ReportModel
import com.anonymous.appilogue.network.ReportApi
import javax.inject.Inject

class ReportRepository @Inject constructor(
    private val reportApi: ReportApi
) : Repository {

    suspend fun reportAbuse(reportModel: ReportModel): UiState<Unit> {
        return try {
            val response = reportApi.reportAbuse(reportModel)

            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }
}