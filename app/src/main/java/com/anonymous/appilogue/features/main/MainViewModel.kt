package com.anonymous.appilogue.features.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anonymous.appilogue.model.InstalledApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var selectedApp: InstalledApp? = null
    private val _bottomNavigationState = MutableLiveData(true)
    val bottomNavigationState: LiveData<Boolean> = _bottomNavigationState

    private val _bottomNavigationClickable = MutableLiveData(true)
    val bottomNavigationClickable: LiveData<Boolean> = _bottomNavigationClickable

    fun hideBottomNavigation() {
        _bottomNavigationState.value = false
    }

    fun showBottomNavigation() {
        _bottomNavigationState.value = true
    }

    fun enableClickBottomNavigation() {
        _bottomNavigationClickable.value = true
    }

    fun disableClickBottomNavigation() {
        _bottomNavigationClickable.value = false
    }

}