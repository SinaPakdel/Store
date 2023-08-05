package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.model.productlist.Products
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    fun getProducts(page: Int, filter: Map<String, String>): Flow<ResponseState<List<Products>>>
}