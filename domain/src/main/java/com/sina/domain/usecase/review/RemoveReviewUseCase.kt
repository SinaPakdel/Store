package com.sina.domain.usecase.review

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.network.model.reviewdto.DeleteReviewDto
import com.sina.domain.repositroy.ReviewRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoveReviewUseCase @Inject constructor(private val reviewRepository: ReviewRepository) :
    UseCaseFlow<RemoveReviewUseCase.Params, DeleteReviewDto>() {
    data class Params(val reviewId: Int)

    override fun execute(params: Params): Flow<DeleteReviewDto> = with(params) {
        reviewRepository.deleteReview(reviewId).safeOpen()
    }
}