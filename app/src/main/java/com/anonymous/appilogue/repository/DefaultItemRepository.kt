package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.SpaceDustItem
import com.anonymous.appilogue.network.api.ItemApi
import timber.log.Timber
import javax.inject.Inject

class DefaultItemRepository @Inject constructor(private val itemApi: ItemApi) :
    ItemRepository {

    override suspend fun fetchSpaceDustItemUrls(): List<SpaceDustItem> {
        try {
            val response = itemApi.fetchSpaceDustItemUrls()
            Timber.d(response.toString())
            response.body()?.let {
                return it
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
        return emptyList()
    }
}
