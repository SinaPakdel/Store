package com.sina.domain.usecase.review

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.review.Review
import com.sina.domain.repositroy.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewUseCase @Inject constructor(private val repository: ReviewRepository) :
    UseCaseFlow<GetReviewUseCase.Params, Review>() {
    data class Params(val reviewId: Int)

    override fun execute(params: Params): Flow<Review> = with(params) {
        repository.getReview(reviewId).safeOpen()
    }
}