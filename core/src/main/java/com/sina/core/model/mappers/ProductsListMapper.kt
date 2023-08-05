package com.sina.core.model.mappers

import com.sina.core.model.productlist.Products
import com.sina.core.network.model.product_list.ProductLstDTOItem

fun mapProductListDtoToProductList(dto: ProductLstDTOItem): Products = with(dto) {
    Products(
        id,
        averageRating,
        images?.map { Products.Image(it?.id, it?.name, it?.src) },
        name,
        price,
        regularPrice,
        salePrice
    )
}
