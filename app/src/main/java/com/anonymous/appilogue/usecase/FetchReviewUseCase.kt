package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.ReviewModel
import com.anonymous.appilogue.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    operator fun invoke(reviewId: Int): Flow<UiState<ReviewModel>> = flow {
        emit(reviewRepository.fetchReview(reviewId))
    }
}