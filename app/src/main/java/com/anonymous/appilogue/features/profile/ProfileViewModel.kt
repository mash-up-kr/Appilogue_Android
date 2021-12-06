package com.anonymous.appilogue.features.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _myBlackHoleReviewCount = MutableLiveData("0")
    val myBlackHoleReviewCount: LiveData<String> = _myBlackHoleReviewCount

    private val _myWhiteHoleReviewCount = MutableLiveData("0")
    val myWhiteHoleReviewCount: LiveData<String> = _myWhiteHoleReviewCount
}