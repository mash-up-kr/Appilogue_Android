package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.AppModel
import com.anonymous.appilogue.repository.SearchAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindAppUseCase @Inject constructor(
    private val searchAppRepository: SearchAppRepository
) {

    operator fun invoke(appName: String?): Flow<UiState<AppModel>> = flow {
        appName?.let {
            emit(searchAppRepository.findAppInfo(it))
        }
    }
}