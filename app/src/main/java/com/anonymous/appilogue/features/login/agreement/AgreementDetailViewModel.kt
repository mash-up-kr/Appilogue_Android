package com.anonymous.appilogue.features.login.agreement

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgreementDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val type = savedStateHandle.get<String>(AGREEMENT_DETAIL_TYPE_KEY)

    fun getDescriptionFileName() = if (type == TERMS) {
        TERMS_FILE_NAME
    } else {
        PERSONAL_INFO_FILE_NAME
    }

    companion object {
        const val AGREEMENT_DETAIL_TYPE_KEY = "agreementDetail"
        const val TERMS = "terms"
        const val PERSONAL_INFO = "personalInfo"
        const val TERMS_FILE_NAME = "terms_agreement.txt"
        const val PERSONAL_INFO_FILE_NAME = "personal_info_agreement.txt"
    }
}