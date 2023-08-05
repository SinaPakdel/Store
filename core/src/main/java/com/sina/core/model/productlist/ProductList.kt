package com.sina.core.model.productlist

import com.sina.core.network.model.product_list.ProductLstDTOItem


data class Products(
    val id: Int?,
    val averageRating: String?,
    val images: List<Image>?,
    val name: String?,
    val price: String?,
    val regularPrice: String?,
    val salePrice: String?
) {
    data class Image(
        val id: Int?,
        val name: String?,
        val src: String?
    )
}

fun mapListOfProductDtoToProduct(listOfProductDto: List<ProductLstDTOItem>): List<Products> = listOfProductDto.map {
    Products(
        id = it.id,
        averageRating = it.averageRating,
        images = it.images?.map { image -> Products.Image(id = image?.id, name = image?.name, src = image?.src) },
        name = it.name,
        price = it.price,
        regularPrice = it.regularPrice,
        salePrice = it.salePrice
    )
}

//fun mapListOfProductsToListOfProductsDto(listOfProducts: List<Products>): List<ProductsDTOItem> {
//    return listOfProducts.map {
//        ProductsDTOItem(
//            id = it.id,
//            averageRating = it.averageRating,
//            images = it.images.map { image -> Image(id = image.id, name = image.name, src = image.src) },
//            name = it.name,
//            price = it.price,
//            regularPrice = it.regularPrice,
//            salePrice = it.salePrice
//        )
//    }
//}