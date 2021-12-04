package com.anonymous.appilogue.features.home.bottomsheet

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.R
import com.anonymous.appilogue.databinding.FragmentMyDecorationBinding
import com.anonymous.appilogue.features.base.BaseFragment
import com.anonymous.appilogue.features.home.HomeViewModel
import com.anonymous.appilogue.model.Review
import com.anonymous.appilogue.model.SelectableSpaceDustItem
import com.anonymous.appilogue.model.SpaceDustItem
import com.anonymous.appilogue.repository.ItemRepository
import com.anonymous.appilogue.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MyDecorationViewModel @Inject constructor(
    private val itemRepository: ItemRepository
) : ViewModel() {

    private val _spaceDustItems = MutableLiveData<List<SelectableSpaceDustItem>>()
    val spaceDustItems: LiveData<List<SelectableSpaceDustItem>> = _spaceDustItems

    fun fetchSpaceDustItems() {
        viewModelScope.launch {
            _spaceDustItems.value = itemRepository.fetchSpaceDustItemUrls()
                .map {
                    SelectableSpaceDustItem(it)
                }
        }
    }

    fun selectItem(position: Int) {
        val newList = _spaceDustItems.value?.toMutableList()
        newList?.let {
            it[position] = it[position].copy(isSelected = true)
            _spaceDustItems.value = it
        }
    }
}