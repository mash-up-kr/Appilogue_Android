package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.repository.ReviewRepository
import javax.inject.Inject

class RemoveCommentUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(commentId: Int): UiState<Unit> {
        return reviewRepository.removeComment(commentId)
    }
}