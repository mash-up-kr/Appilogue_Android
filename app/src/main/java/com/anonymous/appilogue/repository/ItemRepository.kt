package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.SpaceDustItem

interface ItemRepository {
    suspend fun fetchSpaceDustItemUrls(): List<SpaceDustItem>
}