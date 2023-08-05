package com.sina.domain.usecase.product

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.productinfo.ProductInfo
import com.sina.domain.repositroy.ProductInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductInfoUseCase@Inject constructor(private val productInfoRepository: ProductInfoRepository) :
    UseCaseFlow<GetProductInfoUseCase.Params, ProductInfo>() {
    data class Params(val productId: Int)

    override fun execute(params: Params): Flow<ProductInfo> = with(params) {
        productInfoRepository.getProductInfoStream(productId)
    }.safeOpen()
}