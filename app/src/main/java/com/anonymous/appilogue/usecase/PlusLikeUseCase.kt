package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.features.base.UiState
import com.anonymous.appilogue.model.dto.LikeDto
import com.anonymous.appilogue.repository.ReviewRepository
import javax.inject.Inject

class PlusLikeUseCase @Inject constructor(
    private val reviewRepository: ReviewRepository
) {

    suspend operator fun invoke(likeDto: LikeDto): UiState<Unit> {
        return reviewRepository.plusLike(likeDto)
    }
}