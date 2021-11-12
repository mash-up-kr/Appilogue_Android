package com.anonymous.appilogue.usecase

import com.anonymous.appilogue.model.ReviewInfo
import javax.inject.Inject

class FetchReviewListUseCase @Inject constructor(

) {
    operator fun invoke(hole: String, position: Int): List<ReviewInfo> = emptyList()
}