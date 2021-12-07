package com.anonymous.appilogue.features.home.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anonymous.appilogue.R

class OnboardingViewModel : ViewModel() {
    private val _descriptionRes = MutableLiveData(R.string.onboarding_planet_description)
    val descriptionRes: LiveData<Int> = _descriptionRes

    private val _emphasizeTextRes = MutableLiveData(R.string.planet)
    val emphasizeTextRes: LiveData<Int> = _emphasizeTextRes

    fun setDescriptionRes(descriptionRes: Int, emphasizeTextRes: Int) {
        _descriptionRes.value = descriptionRes
        _emphasizeTextRes.value = emphasizeTextRes
    }
}