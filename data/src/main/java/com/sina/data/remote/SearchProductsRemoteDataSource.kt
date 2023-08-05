package com.sina.data.remote

import com.sina.core.network.model.product_list.ProductLstDTOItem

interface SearchProductsRemoteDataSource {
    suspend fun getProductsBySearch(
        page: Int,
        querySearch: String,
        filters: Map<String, String>
    ): List<ProductLstDTOItem>
}