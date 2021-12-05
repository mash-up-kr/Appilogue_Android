package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.CommentDto
import com.anonymous.appilogue.repository.ReviewRepository
import javax.inject.Inject

class RegisterCommentUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {
    suspend operator fun invoke(commentDto: CommentDto): UiState<Unit> {
        return reviewRepository.registerComment(commentDto)
    }
}