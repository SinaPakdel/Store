package com.sina.domain.usecase.search

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.productlist.Products
import com.sina.domain.repositroy.SearchProductsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchProductsUseCase @Inject constructor(private val searchProductsRepository: SearchProductsRepository) :
    UseCaseFlow<GetSearchProductsUseCase.Params, List<Products>>() {
    data class Params(val page: Int, val searchQuery: String, val  filters:Map<String,String>)

    override fun execute(params: Params): Flow<List<Products>> =
        with(params) { searchProductsRepository.getProductsBySearch(page, searchQuery, filters) }.safeOpen()
}