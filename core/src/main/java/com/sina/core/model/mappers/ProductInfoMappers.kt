package com.sina.core.model.mappers

import com.sina.core.model.productinfo.ProductInfo
import com.sina.core.network.model.productinfodto.ProductInfoDto

fun mapProductInfoDtoToProductInfo(dto: ProductInfoDto): ProductInfo = with(dto) {
    ProductInfo(
        id,
        name,
        description,
        price,
        regularPrice,
        salePrice,
        averageRating,
        categories.map { ProductInfo.Category(it.id, it.name) },
        images.map { ProductInfo.Image(it.id, it.name, it.src) },
        ratingCount,
        shortDescription,
        tags.map { ProductInfo.Tag(it.id, it.name) },
        relatedIds,
        weight,
        ProductInfo.Dimensions(dimensions.height, dimensions.length, dimensions.width)
    )
}