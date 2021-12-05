package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.ReviewDto
import com.anonymous.appilogue.repository.ReviewRepository
import javax.inject.Inject

class RegisterReviewUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(reviewDto: ReviewDto): UiState<Unit> {
        return reviewRepository.registerReview(reviewDto)
    }
}