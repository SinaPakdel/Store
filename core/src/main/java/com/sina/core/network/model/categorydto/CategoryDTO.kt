package com.sina.core.network.model.categorydto

import com.google.gson.annotations.SerializedName


class CategoryDTO : ArrayList<CategoryDTOItem>()

data class CategoryDTOItem(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("display")
    val display: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("_links")
    val links: Links?,
    @SerializedName("menu_order")
    val menuOrder: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("parent")
    val parent: Int?,
    @SerializedName("slug")
    val slug: String?,
) {
    data class Collection(
        @SerializedName("href")
        val href: String?
    )

    data class Image(
        @SerializedName("alt")
        val alt: String?,
        @SerializedName("date_created")
        val dateCreated: String?,
        @SerializedName("date_created_gmt")
        val dateCreatedGmt: String?,
        @SerializedName("date_modified")
        val dateModified: String?,
        @SerializedName("date_modified_gmt")
        val dateModifiedGmt: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("src")
        val src: String?
    )

    data class Links(
        @SerializedName("collection")
        val collection: List<Collection?>?,
        @SerializedName("self")
        val self: List<Self?>?,
        @SerializedName("up")
        val up: List<Up?>?
    )

    data class Self(
        @SerializedName("href")
        val href: String?
    )

    data class Up(
        @SerializedName("href")
        val href: String?
    )
}


