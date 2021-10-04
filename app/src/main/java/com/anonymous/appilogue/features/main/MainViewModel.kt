package com.anonymous.appilogue.features.main

import androidx.lifecycle.ViewModel
import com.anonymous.appilogue.model.InstalledApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    var selectedApp: InstalledApp? = null
}