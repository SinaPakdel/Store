package com.sina.data.remote.impl

import com.sina.core.network.model.product_list.ProductLstDTOItem
import com.sina.core.network.response.openResponse
import com.sina.core.network.services.StoreServices
import com.sina.data.remote.ProductsRemoteDataSource

class ProductListRemoteDataSourceImpl(private val storeServices: StoreServices) :
    ProductsRemoteDataSource {
    override suspend fun getProducts(page: Int, filter: Map<String, String>): List<ProductLstDTOItem> =
        storeServices.getProductsList(page, filter).openResponse()
}