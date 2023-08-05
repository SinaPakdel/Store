package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.model.review.Review
import com.sina.core.network.model.reviewdto.DeleteReviewDto
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    fun addReview(review: Review): Flow<ResponseState<Review>>

    fun getReview(reviewId: Int): Flow<ResponseState<Review>>

    fun getReviews(customerEmail: String): Flow<ResponseState<List<Review>>>

    fun deleteReview(reviewId: Int): Flow<ResponseState<DeleteReviewDto>>

    fun updateReview(reviewId: Int, review: Review): Flow<ResponseState<Review>>
}