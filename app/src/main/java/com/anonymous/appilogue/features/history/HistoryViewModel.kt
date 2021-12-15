package com.anonymous.appilogue.features.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anonymous.appilogue.repository.HistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
//    private val historyRepository: HistoryRepository
) : ViewModel() {
    private val _histories = MutableLiveData<List<HistoryListItem>>()
    val histories: LiveData<List<HistoryListItem>> = _histories

//    fun fetchHistories() {
//        val histories = historyRepository.getHistories()
//        _histories.value = TimeByHistory
//            .values()
//            .sortedWith(compareBy { it.order })
//            .map { timeByHistory ->
//                listOf(HistoryListItem.Header(timeByHistory.stringId)) +
//                        histories
//                            .filter { history -> history.date.splitByHistory() == timeByHistory }
//                            .map { history ->
//                                HistoryListItem.HistoryItem(history)
//                            }
//            }
//            .flatten()
//    }
}