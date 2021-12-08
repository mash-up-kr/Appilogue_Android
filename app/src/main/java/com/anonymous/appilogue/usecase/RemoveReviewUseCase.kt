package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.repository.ReviewRepository
import javax.inject.Inject

class RemoveReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(reviewId: Int): UiState<Unit> {
        return reviewRepository.removeReview(reviewId)
    }
}