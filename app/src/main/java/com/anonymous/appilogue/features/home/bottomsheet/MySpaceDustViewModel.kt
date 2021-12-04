package com.anonymous.appilogue.features.home.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anonymous.appilogue.model.SelectableSpaceDustItem
import com.anonymous.appilogue.repository.ItemRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MySpaceDustViewModel @Inject constructor(
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