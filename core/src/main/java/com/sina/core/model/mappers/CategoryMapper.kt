package com.sina.core.model.mappers

import com.sina.core.model.category.Category
import com.sina.core.network.model.categorydto.CategoryDTOItem

fun mapCategoryDTOToCategory(dto: CategoryDTOItem): Category = with(dto) {
    Category(
        description,
        id,
        Category.Image(image?.id, image?.name, image?.src),
        name
    )
}