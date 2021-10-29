package com.anonymous.appilogue.features.history

import com.anonymous.appilogue.model.History

sealed class HistoryListItem {
    abstract val id: Int

    data class HistoryItem(val history: History) : HistoryListItem() {
        override val id = history.id
    }

    class Header(val stringId: Int) : HistoryListItem() {
        override val id = Int.MIN_VALUE
    }
}