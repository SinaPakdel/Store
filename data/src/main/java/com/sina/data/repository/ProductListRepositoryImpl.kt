package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.model.mappers.mapProductListDtoToProductList
import com.sina.core.model.productlist.Products
import com.sina.data.remote.ProductsRemoteDataSource
import com.sina.domain.repositroy.ProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductListRepositoryImpl(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ProductsRepository {
    override fun getProducts(
        page: Int,
        filter: Map<String, String>
    ): Flow<ResponseState<List<Products>>> =
        flow {
            emit(
                productsRemoteDataSource.getProducts(page, filter)
                    .map { mapProductListDtoToProductList(it) })
        }.asResponse().flowOn(dispatcher)
}