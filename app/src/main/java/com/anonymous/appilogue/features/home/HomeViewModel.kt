package com.anonymous.appilogue.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anonymous.appilogue.model.ReviewedApp
import com.anonymous.appilogue.repository.AppRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val appRepository: AppRepository) : ViewModel() {

    private val _starFocused = MutableLiveData(Focus.None)
    val starFocused: LiveData<Focus> = _starFocused

    private val _bottomSheetState = MutableLiveData(BottomSheetBehavior.STATE_HIDDEN)
    val bottomSheetState: LiveData<Int> = _bottomSheetState

    private val _bottomSheetHideable = MutableLiveData(true)
    val bottomSheetHideable: LiveData<Boolean> = _bottomSheetHideable

    private val _blackHoleApps = MutableLiveData(listOf<ReviewedApp>())
    val blackHoleApps: LiveData<List<ReviewedApp>> = _blackHoleApps

    private val _whiteHoleApps = MutableLiveData(listOf<ReviewedApp>())
    val whiteHoleApps: LiveData<List<ReviewedApp>> = _whiteHoleApps

    private val _bottomSheetTabFocus = MutableLiveData(1)
    val bottomSheetTabFocus: LiveData<Int> = _bottomSheetTabFocus

    fun changeBottomSheetState(newState: Int) {
        _bottomSheetState.value = newState
    }

    fun changeFocus(focus: Focus) {
        if (focus == Focus.None) {
            starFocused.value?.let {
                _starFocused.value = Focus.toOffFocus(it)
            }
        }
        _starFocused.value = focus
        if (Focus.isOnFocus(focus)) {
            _bottomSheetHideable.value = false
            changeBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED)
        } else {
            _bottomSheetHideable.value = true
            changeBottomSheetState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    fun fetchBlackHoleApps() {
        _blackHoleApps.value = appRepository.getBlackHoleApps()
    }

    fun fetchWhiteHoleApps() {
        _whiteHoleApps.value = appRepository.getWhiteHoleApps()
    }

    fun focusbottomSheetTab(index: Int) {
        when (index) {
            in 1..2 -> {
                _bottomSheetTabFocus.value = index
            }
            else -> {
            }
        }
    }
}