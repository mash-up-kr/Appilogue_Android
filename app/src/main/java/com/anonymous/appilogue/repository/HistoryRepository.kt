package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.HistoryModel


interface HistoryRepository {
    fun getHistories(): List<HistoryModel>
}