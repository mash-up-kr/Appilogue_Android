package com.anonymous.appilogue.features.login.agreement

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AgreementViewModel @Inject constructor(

) : ViewModel() {
    private val _areAllChecked = MutableStateFlow(false)
    val areAllChecked: StateFlow<Boolean> = _areAllChecked

    private val _isTermsChecked = MutableStateFlow(false)
    val isTermsChecked: StateFlow<Boolean> = _isTermsChecked

    private val _isPersonalInfoChecked = MutableStateFlow(false)
    val isPersonalInfoChecked: StateFlow<Boolean> = _isPersonalInfoChecked

    fun checkAllAgreement() {
        val allCheck = !areAllChecked.value
        _areAllChecked.value = allCheck
        _isTermsChecked.value = allCheck
        _isPersonalInfoChecked.value = allCheck
    }

    fun checkTermsAgreement() {
        _isTermsChecked.value = !isTermsChecked.value
        _areAllChecked.value = isTermsChecked.value && isPersonalInfoChecked.value
    }

    fun checkPersonalInfoAgreement() {
        _isPersonalInfoChecked.value = !isPersonalInfoChecked.value
        _areAllChecked.value = isTermsChecked.value && isPersonalInfoChecked.value
    }
}