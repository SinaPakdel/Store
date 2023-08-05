package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.model.category.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getTopRatedProducts(page: Int, orderBy: String): Flow<ResponseState<List<Category>>>
}