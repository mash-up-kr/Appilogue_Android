package com.anonymous.appilogue.network.api

import com.anonymous.appilogue.model.SpaceDustItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ItemApi {

    @GET
    suspend fun fetchSpaceDustItemUrls(
        @Url url: String = "https://cdn.moussg.io/data/item-url.json"
    ): Response<List<SpaceDustItem>>
}