package com.sina.data.remote

import com.sina.core.network.model.reviewdto.DeleteReviewDto
import com.sina.core.network.model.reviewdto.ReviewDto

interface ReviewRemoteDataSource {
    suspend fun addReview(reviewDto: ReviewDto): ReviewDto

    suspend fun getReview(reviewId: Int): ReviewDto

    suspend fun getReviews(customerEmail: String): List<ReviewDto>

    suspend fun deleteReview(reviewId: Int): DeleteReviewDto

    suspend fun updateReview(reviewId: Int, reviewDto: ReviewDto): ReviewDto

}