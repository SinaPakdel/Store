package com.sina.domain.usecase.review

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.review.Review
import com.sina.domain.repositroy.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddReviewUseCase @Inject constructor(private val reviewRepository: ReviewRepository) :
    UseCaseFlow<AddReviewUseCase.Params, Review>() {
    data class Params(val review: Review)

    override fun execute(params: Params): Flow<Review> = with(params) {
        reviewRepository.addReview(review).safeOpen()
    }
}