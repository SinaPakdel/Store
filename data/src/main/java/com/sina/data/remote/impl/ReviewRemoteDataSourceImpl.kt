package com.sina.data.remote.impl

import com.sina.core.network.model.reviewdto.DeleteReviewDto
import com.sina.core.network.model.reviewdto.ReviewDto
import com.sina.data.remote.ReviewRemoteDataSource
import com.sina.core.network.response.openResponse
import com.sina.core.network.services.StoreServices

class ReviewRemoteDataSourceImpl(private val storeServices: StoreServices) :
    ReviewRemoteDataSource {
    override suspend fun addReview(reviewDto: ReviewDto): ReviewDto =
        storeServices.createReview(reviewDto).openResponse()

    override suspend fun getReview(reviewId: Int): ReviewDto =
        storeServices.getReview(reviewId).openResponse()

    override suspend fun getReviews(customerEmail: String): List<ReviewDto> =
        storeServices.getReviewProductsByCustomer(customerEmail).openResponse()

    override suspend fun deleteReview(reviewId: Int): DeleteReviewDto =
        storeServices.deleteReview(reviewId).openResponse()

    override suspend fun updateReview(reviewId: Int, reviewDto: ReviewDto): ReviewDto =
        storeServices.updateReview(reviewId, reviewDto).openResponse()
}