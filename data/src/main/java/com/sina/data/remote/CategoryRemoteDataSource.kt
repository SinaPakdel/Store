package com.sina.data.remote

import com.sina.core.network.model.categorydto.CategoryDTOItem


interface CategoryRemoteDataSource {
    suspend fun getCategoriesList(page: Int, orderBy: String): List<CategoryDTOItem>
}
