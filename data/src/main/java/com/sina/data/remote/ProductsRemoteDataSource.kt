package com.sina.data.remote

import com.sina.core.network.model.product_list.ProductLstDTOItem

interface ProductsRemoteDataSource {
    suspend fun getProducts(page: Int, filter: Map<String, String>): List<ProductLstDTOItem>
}