package com.anonymous.appilogue.features.community.reviewlist

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.anonymous.appilogue.model.ReviewModel
import com.anonymous.appilogue.usecase.FetchReviewListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReviewListDataProvider @Inject constructor(
    private val fetchReviewListUseCase: FetchReviewListUseCase
) {

    fun getPagingData(hole: String): Flow<PagingData<ReviewModel>> =
        Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = { ReviewListPagingSource(fetchReviewListUseCase, hole) }
        ).flow

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
    }
}