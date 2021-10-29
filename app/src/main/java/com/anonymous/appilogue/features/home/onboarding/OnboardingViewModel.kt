package com.anonymous.appilogue.features.home.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnboardingViewModel : ViewModel() {
    private val _description = MutableLiveData(EMPTY_STRING)
    val description: LiveData<String> = _description

    companion object {
        const val EMPTY_STRING = ""
    }
}