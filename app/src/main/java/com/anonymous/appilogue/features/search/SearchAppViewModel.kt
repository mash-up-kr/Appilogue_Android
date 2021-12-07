package com.anonymous.appilogue.features.search

import androidx.lifecycle.*
import com.anonymous.appilogue.features.base.isSuccessful
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.ImageApiResponse
import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.repository.SearchAppRepository
import com.anonymous.appilogue.usecase.UploadAppIconImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SearchAppViewModel @Inject constructor(
    private val searchAppRepository: SearchAppRepository,
    private val uploadAppIconImage: UploadAppIconImage
) : ViewModel() {
    private val installedAppList = MutableStateFlow<List<InstalledApp>>(emptyList())

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event: SharedFlow<Event> = _event

    val inputKeyword = MutableLiveData<String>()

    private val _searchResult = MutableLiveData<List<InstalledApp>>()
    val searchResult: LiveData<List<InstalledApp>> = _searchResult

    init {
        fetchInstalledAppList()
    }

    private fun fetchInstalledAppList() {
        viewModelScope.launch {
            installedAppList.value = searchAppRepository.fetchInstalledAppList()
        }
    }

    fun search(keyword: String) {
        Timber.d("Search : $keyword")
        _searchResult.value = if (keyword.isNotBlank()) {
            installedAppList.value.filter { app ->
                app.name.lowercase().contains(keyword.lowercase())
            }
        } else {
            emptyList()
        }
    }

    fun uploadAppIconAndMoveToNextPage(cacheDir: File, appItem: InstalledApp) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = uploadAppIconImage(cacheDir, appItem.icon)
            if (result.isSuccessful) {
                handleEvent(Event.MoveToReviewSelector(appItem.name, result.successOr(ImageApiResponse()).url))
            }
        }
    }

    private fun handleEvent(event: Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    sealed class Event {
        data class MoveToReviewSelector(val appName: String, val appIconUrl: String) : Event()
    }
}
