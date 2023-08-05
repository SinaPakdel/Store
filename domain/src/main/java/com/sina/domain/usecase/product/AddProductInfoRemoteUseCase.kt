package com.sina.domain.usecase.product

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.order.Order
import com.sina.domain.repositroy.ProductInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductInfoRemoteUseCase@Inject constructor(
    private val productInfoRepository: ProductInfoRepository,
) : UseCaseFlow<AddProductInfoRemoteUseCase.Params, Order>() {
    data class Params(val order: Order)

    override fun execute(params: Params): Flow<Order> = with(params) {
        productInfoRepository.createProductInfoRemote(order).safeOpen()
    }
}