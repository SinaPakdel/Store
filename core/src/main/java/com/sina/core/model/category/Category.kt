package com.sina.core.model.category

data class Category(
    val description: String?,
    val id: Int?,
    val image: Image?,
    val name: String?,
) {
    data class Image(
        val id: Int?,
        val name: String?,
        val src: String?
    )
}