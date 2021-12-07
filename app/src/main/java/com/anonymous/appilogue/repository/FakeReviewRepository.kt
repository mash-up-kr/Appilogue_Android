package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.Review
import com.anonymous.appilogue.network.api.SearchApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class FakeReviewRepository @Inject constructor(private val searchApi: SearchApi) :
    ReviewRepository {

    override suspend fun fetchMyWhiteHoleReview(): List<Review> {
        try {
            val response = searchApi.fetchReview(1, "white", 1, 30)
            response.body()?.reviews?.let {
                return it
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
        return emptyList()
    }

    override suspend fun fetchMyBlackHoleReview(): List<Review> {
        try {
            val response = searchApi.fetchReview(1, "black", 1, 30)
            Timber.d(response.toString())
            response.body()?.reviews?.let {
                return it
            }
        } catch (e: Throwable) {
            Timber.e(e)
        }
        return emptyList()
    }

}
