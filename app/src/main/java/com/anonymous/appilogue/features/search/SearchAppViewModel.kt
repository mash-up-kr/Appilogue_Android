package com.anonymous.appilogue.features.search

import androidx.lifecycle.*
import com.anonymous.appilogue.model.InstalledApp
import com.anonymous.appilogue.repository.SearchAppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchAppViewModel @Inject constructor(
    private val searchAppRepository: SearchAppRepository
) : ViewModel() {
    private var installedAppList: LiveData<List<InstalledApp>> = searchAppRepository.fetchInstalledAppList().asLiveData()

    val inputKeyword = MutableLiveData<String>()

    private val _searchResult = MutableLiveData<List<InstalledApp>>()
    val searchResult: LiveData<List<InstalledApp>> = _searchResult

    fun search(keyword: String) {
        Timber.d("Search : $keyword")
        _searchResult.value = if (keyword.isNotBlank()) {
            installedAppList.value?.filter { app ->
                app.name.lowercase().contains(keyword.lowercase())
            } ?: emptyList()
        } else {
            emptyList()
        }
    }
}