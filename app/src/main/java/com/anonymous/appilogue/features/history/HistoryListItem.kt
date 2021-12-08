package com.anonymous.appilogue.features.history

import com.anonymous.appilogue.model.HistoryModel

sealed class HistoryListItem {
    abstract val id: Int

    data class HistoryItem(val history: HistoryModel) : HistoryListItem() {
        override val id = history.id
    }

    class Header(val stringId: Int) : HistoryListItem() {
        override val id = Int.MIN_VALUE
    }
}