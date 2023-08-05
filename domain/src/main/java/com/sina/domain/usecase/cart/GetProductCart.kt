package com.sina.domain.usecase.cart

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.productlist.Products
import com.sina.domain.repositroy.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductCart @Inject constructor(private val cartRepository: CartRepository) :
    UseCaseFlow<GetProductCart.Params, List<Products>>() {
    data class Params(val ids: Int)

    override fun execute(params: Params): Flow<List<Products>> = with(params) {
        cartRepository.getListOfProductOfCart(ids).safeOpen()
    }
}