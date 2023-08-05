package com.sina.domain.usecase.product

import com.sina.core.common.interactor.UseCase
import com.sina.core.local.model.OrderEntity
import com.sina.domain.repositroy.ProductInfoRepository
import javax.inject.Inject

class AddProductInfoLocalUseCase @Inject constructor(private val productInfoRepository: ProductInfoRepository) :
    UseCase<AddProductInfoLocalUseCase.Params, Unit>() {
    data class Params(val productId: Int, val quantity: Int)

    override suspend fun execute(params: Params) =
        with(params) { productInfoRepository.addProductInfoLocal(OrderEntity(productId, quantity)) }

}