package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.CommentModel
import com.anonymous.appilogue.repository.ReviewRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCommentsUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(reviewId: Int, commentId: Int): Flow<UiState<List<CommentModel>>> = flow {
        emit(reviewRepository.fetchComments(reviewId, commentId))
    }
}