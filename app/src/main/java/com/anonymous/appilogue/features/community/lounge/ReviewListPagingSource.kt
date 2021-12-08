package com.anonymous.appilogue.features.community.lounge

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.anonymous.appilogue.features.base.successOr
import com.anonymous.appilogue.model.ReviewModel
import com.anonymous.appilogue.usecase.FetchReviewListUseCase

class ReviewListPagingSource(
    private val fetchReviewListUseCaseUseCase: FetchReviewListUseCase,
    private val hole: String
) : PagingSource<Int, ReviewModel>() {

    override fun getRefreshKey(state: PagingState<Int, ReviewModel>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ReviewModel> {
        val position = params.key ?: INITIAL_LOAD_PAGE
        return try {
            val response = fetchReviewListUseCaseUseCase(hole, position)
            val result = response.successOr(emptyList())
            val prevKey = if (position == INITIAL_LOAD_PAGE) null else position - 1
            val nextKey = if (result.isNotEmpty()) {
                position + 1
            } else {
                null
            }

            LoadResult.Page(
                data = result,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INITIAL_LOAD_PAGE = 1
    }
}