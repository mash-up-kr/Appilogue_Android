package com.anonymous.appilogue.features.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.model.ReviewedApp
import com.anonymous.appilogue.repository.AppRepository
import com.anonymous.appilogue.repository.ReviewRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _starFocused = MutableLiveData(Focus.None)
    val starFocused: LiveData<Focus> = _starFocused

    private val _bottomSheetState = MutableLiveData(BottomSheetBehavior.STATE_HIDDEN)
    val bottomSheetState: LiveData<Int> = _bottomSheetState

    private val _bottomSheetHideable = MutableLiveData(true)
    val bottomSheetHideable: LiveData<Boolean> = _bottomSheetHideable

    private val _apps = MutableLiveData(emptyList<ReviewedApp>())
    val apps: LiveData<List<ReviewedApp>> = _apps

    private val _starsAlpha =
        MutableLiveData<StarEmphasizeState>(StarEmphasizeState.EmphasizeOnAllStar())
    val starsAlpha: LiveData<StarEmphasizeState> = _starsAlpha

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
            changeBottomSheetState(BottomSheetBehavior.STATE_COLLAPSED)
            viewModelScope.launch {
                delay(10)
                _bottomSheetHideable.value = false
            }
        } else {
            _bottomSheetHideable.value = true
            changeBottomSheetState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    fun fetchBlackHoleApps() {
        _apps.value = appRepository.getBlackHoleApps()
    }

    fun fetchWhiteHoleApps() {
        _apps.value = appRepository.getWhiteHoleApps()
    }

    fun setStarsAlpha(focus: Focus, alpha: Float) {
        when (focus) {
            Focus.OnPlanet -> _starsAlpha.value =
                StarEmphasizeState.EmphasizeOnPlanet()
            Focus.OnWhiteHole -> _starsAlpha.value =
                StarEmphasizeState.EmphasizeOnWhiteHole()
            Focus.OnBlackHole -> _starsAlpha.value =
                StarEmphasizeState.EmphasizeOnBlackHole()
            Focus.OnSpaceDust -> _starsAlpha.value =
                StarEmphasizeState.EmphasizeOnSpaceDust()
            Focus.None -> _starsAlpha.value = StarEmphasizeState.EmphasizeOnAllStar()
        }
    }
}
