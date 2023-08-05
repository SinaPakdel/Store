package com.sina.data.remote.impl

import com.sina.core.network.model.categorydto.CategoryDTOItem
import com.sina.core.network.response.openResponse
import com.sina.data.remote.CategoryRemoteDataSource
import com.sina.core.network.services.StoreServices

class CategoryRemoteDataSourceImpl(private val storeServices: StoreServices) :
    CategoryRemoteDataSource {
//    override suspend fun getCategoriesList(page: Int, orderBy: String): List<Category> =
//        storeServices.getCategories().map { mapCategoryDTOToCategory(it) }

    override suspend fun getCategoriesList(page: Int, orderBy: String): List<CategoryDTOItem> =
        storeServices.getCategories().openResponse()
}