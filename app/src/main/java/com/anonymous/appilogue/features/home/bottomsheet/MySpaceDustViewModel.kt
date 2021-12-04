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

    private val _selectedSpaceDustImageUrl = MutableLiveData(DEFAULT_SPACE_DUST_IMAGE_URL)
    val selectedSpaceDustImageUrl: LiveData<String> = _selectedSpaceDustImageUrl

    fun fetchSpaceDustItems() {
        viewModelScope.launch {
            _spaceDustItems.value = itemRepository.fetchSpaceDustItemUrls()
                .map {
                    SelectableSpaceDustItem(it)
                }
            // TODO 내 URL과 비교하여 같으면 selected를 true로 한다.
        }
    }

    fun selectItem(newlySelectedPosition: Int) {
        val newList = _spaceDustItems.value?.toMutableList()
        newList?.let { _newList ->
            val alreadySelectedPosition = _newList.indexOf(_newList.find { it.isSelected })
            if (alreadySelectedPosition != -1) {
                _newList[alreadySelectedPosition] =
                    _newList[alreadySelectedPosition].copy(isSelected = false)
                if (newlySelectedPosition == alreadySelectedPosition) {
                    _newList[newlySelectedPosition] =
                        _newList[newlySelectedPosition].copy(isSelected = false)
                    _selectedSpaceDustImageUrl.value =
                        DEFAULT_SPACE_DUST_IMAGE_URL
                } else {
                    _newList[newlySelectedPosition] =
                        _newList[newlySelectedPosition].copy(isSelected = true)
                    _selectedSpaceDustImageUrl.value =
                        _newList[newlySelectedPosition].spaceDustItem.dressedUrlAndroid
                }
            } else {
                _newList[newlySelectedPosition] =
                    _newList[newlySelectedPosition].copy(isSelected = true)
                _selectedSpaceDustImageUrl.value =
                    _newList[newlySelectedPosition].spaceDustItem.dressedUrlAndroid
            }
            _spaceDustItems.value = _newList
        }
    }

    companion object {
        const val DEFAULT_SPACE_DUST_IMAGE_URL =
            "https://cdn.moussg.io/upload/e4b3749a-8690-47bb-94c0-b77cfed0edaa.png"
    }
}