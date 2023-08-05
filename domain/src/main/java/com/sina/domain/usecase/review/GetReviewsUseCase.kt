package com.sina.domain.usecase.review

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.review.Review
import com.sina.domain.repositroy.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsUseCase@Inject constructor(private val reviewRepository: ReviewRepository) :
    UseCaseFlow<GetReviewsUseCase.Params, List<Review>>() {
    data class Params(val customerEmail: String)

    override fun execute(params: Params): Flow<List<Review>> = with(params) {
        reviewRepository.getReviews(customerEmail).safeOpen()
    }
}