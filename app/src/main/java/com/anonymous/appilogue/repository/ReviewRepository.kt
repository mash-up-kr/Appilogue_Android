package com.anonymous.appilogue.repository

import com.anonymous.appilogue.exceptions.ClientErrorException
import com.anonymous.appilogue.exceptions.EmptyResponseException
import com.anonymous.appilogue.exceptions.ServerErrorException
import com.anonymous.appilogue.exceptions.UnknownException
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.ReviewInfo
import com.anonymous.appilogue.network.ReviewApi
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val reviewApi: ReviewApi
) {

    suspend fun fetchReviews(
        hole: String = "",
        page: Int = 1,
    ): UiState<List<ReviewInfo>> {
        try {
            val response = reviewApi.searchReviews(hole, page)

            if (response.isSuccessful) {
                val apiResponse = response.body() ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                return UiState.Success(apiResponse.items)
            } else {
                when (response.code()) {
                    in 400..499 -> throw ClientErrorException(
                        response.code(),
                        response.raw().message()
                    )
                    in 500..599 -> throw ServerErrorException(
                        response.code(),
                        response.raw().message()
                    )
                    else -> throw UnknownException(response.code(), response.raw().message())
                }
            }
        } catch (e: Exception) {
            return UiState.Failure(e)
        }
    }
}
