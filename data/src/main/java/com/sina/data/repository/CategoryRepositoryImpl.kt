package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.model.category.Category
import com.sina.core.model.mappers.mapCategoryDTOToCategory
import com.sina.data.remote.CategoryRemoteDataSource
import com.sina.domain.repositroy.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryRepositoryImpl(
    private val categoryRemoteDataSource: CategoryRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,
) : CategoryRepository {
    override fun getTopRatedProducts(
        page: Int,
        orderBy: String
    ): Flow<ResponseState<List<Category>>> = flow {
        emit(
            categoryRemoteDataSource.getCategoriesList(page, orderBy)
                .map { mapCategoryDTOToCategory(it) })
    }.asResponse().flowOn(dispatcher)
}