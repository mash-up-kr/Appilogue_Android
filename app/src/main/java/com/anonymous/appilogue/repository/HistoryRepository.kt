package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.History


interface HistoryRepository {
    fun getHistories(): List<History>
}