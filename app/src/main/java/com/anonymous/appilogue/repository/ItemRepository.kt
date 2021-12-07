package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.Review
import com.anonymous.appilogue.model.SpaceDustItem
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun fetchSpaceDustItemUrls(): List<SpaceDustItem>
}