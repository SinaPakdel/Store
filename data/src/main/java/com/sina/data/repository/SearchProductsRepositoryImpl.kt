package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.model.mappers.mapProductListDtoToProductList
import com.sina.core.model.productlist.Products
import com.sina.data.remote.SearchProductsRemoteDataSource
import com.sina.domain.repositroy.SearchProductsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchProductsRepositoryImpl(
    private val searchProductsRemoteDataSource: SearchProductsRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : SearchProductsRepository {
    override fun getProductsBySearch(
        page: Int,
        searchQuery: String,
        filters: Map<String, String>
    ): Flow<ResponseState<List<Products>>> =
        flow {
            emit(
                searchProductsRemoteDataSource.getProductsBySearch(
                    page,
                    searchQuery,
                    filters
                ).map { mapProductListDtoToProductList(it) }
            )
        }.asResponse().flowOn(dispatcher)

}