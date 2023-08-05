package com.sina.domain.usecase.category

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.category.Category
import com.sina.domain.repositroy.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor (private val categoryRepository: CategoryRepository) :
    UseCaseFlow<CategoryUseCase.Params, List<Category>>() {

    data class Params(
        val page: Int,
        val orderBy: String,
    )

    override fun execute(params: Params): Flow<List<Category>> =
        with(params){categoryRepository.getTopRatedProducts(page,orderBy)}.safeOpen()
}