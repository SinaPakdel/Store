package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.model.mappers.mapReviewDtoToReview
import com.sina.core.model.mappers.mapReviewToReviewDto
import com.sina.core.model.review.Review
import com.sina.core.network.model.reviewdto.DeleteReviewDto
import com.sina.core.network.model.reviewdto.ReviewDto
import com.sina.data.remote.ReviewRemoteDataSource
import com.sina.domain.repositroy.ReviewRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ReviewRepositoryImpl(
    private val reviewRemoteDataSource: ReviewRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ReviewRepository {
    override fun addReview(review: Review): Flow<ResponseState<Review>> = flow {
        val reviewDto: ReviewDto = reviewRemoteDataSource.addReview(mapReviewToReviewDto(review))
        emit(mapReviewDtoToReview(reviewDto))
    }.asResponse().flowOn(dispatcher)

    override fun getReviews(customerEmail: String): Flow<ResponseState<List<Review>>> = flow {
        emit(reviewRemoteDataSource.getReviews(customerEmail).map { mapReviewDtoToReview(it) })
    }.asResponse().flowOn(dispatcher)

    override fun getReview(reviewId: Int): Flow<ResponseState<Review>> = flow {
        emit(mapReviewDtoToReview(reviewRemoteDataSource.getReview(reviewId)))
    }.asResponse().flowOn(dispatcher)

    override fun deleteReview(reviewId: Int): Flow<ResponseState<DeleteReviewDto>> = flow {
        emit(reviewRemoteDataSource.deleteReview(reviewId))
    }.asResponse().flowOn(dispatcher)

    override fun updateReview(reviewId: Int, review: Review): Flow<ResponseState<Review>> = flow {
        emit(
            mapReviewDtoToReview(
                reviewRemoteDataSource.updateReview(
                    reviewId,
                    mapReviewToReviewDto(review)
                )
            )
        )
    }.asResponse().flowOn(dispatcher)
}