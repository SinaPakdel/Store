package com.sina.domain.usecase.product

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.network.model.reviewdto.ReviewDto
import com.sina.domain.repositroy.ProductInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductReviewList @Inject constructor(private val productInfoRepository: ProductInfoRepository) :
    UseCaseFlow<GetProductReviewList.Params, List<ReviewDto>>() {
    data class Params(val productId: Int)

    override fun execute(params: Params): Flow<List<ReviewDto>> = with(params) {
        productInfoRepository.getProductReviewList(productId)
    }.safeOpen()
}