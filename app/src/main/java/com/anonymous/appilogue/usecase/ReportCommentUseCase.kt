package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.ReportModel
import com.anonymous.appilogue.repository.ReportRepository
import javax.inject.Inject

class ReportCommentUseCase @Inject constructor(
    private val reportRepository: ReportRepository
) {

    suspend operator fun invoke(reportModel: ReportModel): UiState<Unit> {
        return reportRepository.reportAbuse(reportModel)
    }
}