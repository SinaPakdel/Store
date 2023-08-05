package com.sina.domain.usecase.cart

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.local.model.OrderEntity
import com.sina.domain.repositroy.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListOfProductInfoLocalUseCase @Inject constructor(
    private val cartRepository: CartRepository
) : UseCaseFlow<Unit, List<OrderEntity>>() {
    override fun execute(params: Unit): Flow<List<OrderEntity>> =
        cartRepository.getListOfOrderLocal().safeOpen()
}