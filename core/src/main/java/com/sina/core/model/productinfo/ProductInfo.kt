package com.sina.core.model.productinfo


data class ProductInfo(
    val id: Int,
    val name: String,
    val description: String?,
    val price: String?,
    val regularPrice: String?,
    val salePrice: String?,
    val averageRating: String?,
    val categories: List<Category>,
    val images: List<Image>,
    val ratingCount: Int?,
    val shortDescription: String?,
    val tags: List<Tag>,
    val relatedIds: List<Int>,
    val weight: String?,
    val dimensions: Dimensions?,
) {
    data class Category(
        val id: Int?,
        val name: String?,
    )

    data class Dimensions(
        val height: String?,
        val length: String?,
        val width: String?,
    )

    data class Image(
        val id: Int?,
        val name: String?,
        val src: String?,
    )

    data class Tag(
        val id: Int?,
        val name: String?,
    )
}

