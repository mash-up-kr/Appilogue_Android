package com.anonymous.appilogue.features.appinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.AppModel
import com.anonymous.appilogue.usecase.FindAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val findAppInfoUseCase: FindAppUseCase
) : ViewModel() {
    val simpleInfo: AppModel = savedStateHandle.get<AppModel>(APP_INFO_KEY)!!

    private val _uiState: MutableStateFlow<UiState<AppModel>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<AppModel>> = _uiState

    init {
        findAppInfo()
    }

    val appInfo: StateFlow<AppModel> = uiState.mapLatest { state ->
        state.successOr(AppModel())
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = AppModel()
    )

    private fun findAppInfo() {
        viewModelScope.launch {
            findAppInfoUseCase(simpleInfo.name).collect {
                _uiState.value = it
            }
        }
    }

    fun isBlackHoleGreater() = appInfo.value.run {
        reviewCountBlack > reviewCountWhite
    }

    fun getBlackHoleRatio(): Int = appInfo.value.run {
        (reviewCountBlack / (reviewCountBlack + reviewCountWhite.toFloat()) * 100).toInt()
    }

    fun getWhiteHoleRatio(): Int = appInfo.value.run {
        (reviewCountWhite / (reviewCountBlack + reviewCountWhite.toFloat()) * 100).toInt()
    }

    companion object {
        private const val APP_INFO_KEY = "appInfo"
    }
}