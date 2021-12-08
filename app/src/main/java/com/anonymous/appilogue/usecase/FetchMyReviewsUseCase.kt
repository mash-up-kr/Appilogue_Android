package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.ReviewModel
import com.anonymous.appilogue.persistence.PreferencesManager
import com.anonymous.appilogue.repository.ReviewRepository
import javax.inject.Inject

class FetchMyReviewsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(hole: String?): UiState<List<ReviewModel>> {
        val myId = PreferencesManager.getMyId() ?: return UiState.Failure(IllegalStateException("Please, Sign-in."))
        return if (hole.isNullOrEmpty()) {
            reviewRepository.fetchMyReviews(myId)
        } else {
            reviewRepository.fetchReviewsByUser(myId, hole)
        }
    }
}