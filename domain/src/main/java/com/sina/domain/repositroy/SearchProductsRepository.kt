package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.model.productlist.Products
import kotlinx.coroutines.flow.Flow

interface SearchProductsRepository {
    fun getProductsBySearch(page: Int, searchQuery: String,  filters:Map<String,String>): Flow<ResponseState<List<Products>>>
}