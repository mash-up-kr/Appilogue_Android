package com.anonymous.appilogue.repository

import com.anonymous.appilogue.model.Review
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun fetchMyWhiteHoleReview(): List<Review>
    suspend fun fetchMyBlackHoleReview(): List<Review>
}