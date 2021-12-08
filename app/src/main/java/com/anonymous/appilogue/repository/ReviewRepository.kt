package com.anonymous.appilogue.repository

import com.anonymous.appilogue.exceptions.EmptyResponseException
import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.*
import com.anonymous.appilogue.model.dto.CommentDto
import com.anonymous.appilogue.model.dto.ImageDto
import com.anonymous.appilogue.model.dto.LikeDto
import com.anonymous.appilogue.model.dto.ReviewDto
import com.anonymous.appilogue.network.api.CommentApi
import com.anonymous.appilogue.network.api.ImageApi
import com.anonymous.appilogue.network.api.ReviewApi
import com.anonymous.appilogue.network.api.SearchApi
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class ReviewRepository @Inject constructor(
    private val searchApi: SearchApi,
    private val reviewApi: ReviewApi,
    private val commentApi: CommentApi,
    private val imageApi: ImageApi
) : Repository {

    suspend fun fetchMyReviews(userId: Int): UiState<List<ReviewModel>> {
        return try {
            val response = searchApi.searchReviews(userId,  1, 1000)

            if (response.isSuccessful) {
                val apiResponse = response.body() ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                UiState.Success(apiResponse.items)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun fetchReviewsByUser(userId: Int, hole: String): UiState<List<ReviewModel>> {
        return try {
            val response = searchApi.searchReviews(userId, hole, 1, 30)

            if (response.isSuccessful) {
                val apiResponse = response.body() ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                UiState.Success(apiResponse.items)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun fetchReviews(
        hole: String = "",
        page: Int = 1,
    ): UiState<List<ReviewModel>> {
        return try {
            val response = searchApi.searchReviews(hole, page)

            if (response.isSuccessful) {
                val apiResponse = response.body() ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                UiState.Success(apiResponse.items)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun fetchReview(
        reviewId: Int
    ): UiState<ReviewModel> {
        return try {
            val response = reviewApi.searchReview(reviewId)

            if (response.isSuccessful) {
                val reviewInfo = response.body() ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                UiState.Success(reviewInfo)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun registerReview(
        reviewDto: ReviewDto
    ): UiState<Unit> {
        return try {
            val response = reviewApi.registerReview(reviewDto)

            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun removeReview(
        reviewId: Int
    ): UiState<Unit> {
        return try {
            val response = reviewApi.removeReview(reviewId)

            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun plusLike(
        likeDto: LikeDto
    ): UiState<Unit> {
        return try {
            val response = reviewApi.plusLike(likeDto)

            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun fetchComments(
        reviewId: Int,
        commentId: Int
    ): UiState<List<CommentModel>> {
        return try {
            val response = commentApi.fetchComments(reviewId)

            if (response.isSuccessful) {
                val commentById = response.body()?.find { it.id == commentId } ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                val result: List<CommentModel> = listOf(commentById) + commentById.children
                UiState.Success(result)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun registerComment(
        commentDto: CommentDto
    ): UiState<Unit> {
        return try {
            val response = commentApi.registerComment(commentDto)

            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun removeComment(
        commentId: Int
    ): UiState<Unit> {
        return try {
            val response = commentApi.removeComment(commentId)

            if (response.isSuccessful) {
                UiState.Success(Unit)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }

    suspend fun uploadAppIcon(
        maxKB: Int,
        format: String,
        imageFile: File
    ): UiState<ImageDto> {
        return try {
            val requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile)
            val appIconPart = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
            val response = imageApi.uploadImage(maxKB, format, appIconPart)

            if (response.isSuccessful) {
                val apiResponse = response.body() ?: throw EmptyResponseException(
                    response.code(),
                    response.raw().message()
                )
                UiState.Success(apiResponse)
            } else {
                handleApiError(response)
            }
        } catch (e: Exception) {
            UiState.Failure(e)
        }
    }
}
