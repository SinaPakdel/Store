package com.sina.data.remote.impl

import com.sina.core.network.model.product_list.ProductLstDTOItem
import com.sina.core.network.response.openResponse
import com.sina.core.network.services.StoreServices
import com.sina.data.remote.SearchProductsRemoteDataSource

class SearchProductsRemoteDataSourceImpl(private val storeServices: StoreServices) :
    SearchProductsRemoteDataSource {
    override suspend fun getProductsBySearch(
        page: Int,
        querySearch: String,
        filters: Map<String, String>
    ): List<ProductLstDTOItem> =
        storeServices.getProductsBySearch(page, querySearch, filters).openResponse()
}