package com.sina.core.network.model.orderdto


import com.google.gson.annotations.SerializedName

data class OrderDto(
    val id: Int,
    val status: String?,
    val total: String,
//    @SerializedName("total_tax")
//    val totalTax: String,
    @SerializedName("line_items")
    val lineItems: List<LineItem>,
    @SerializedName("date_modified_gmt")
    val date: String,
    @SerializedName("billing")
    val billing: Billing?,
    val discount_total: String?,
) {
    data class LineItem(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("price")
        val price: Double?,
        @SerializedName("product_id")
        val productId: Int?,
        @SerializedName("quantity")
        val quantity: Int?,
        @SerializedName("total")
        val total: String?,
        @SerializedName("image")
        val image: Image
    )

    data class Image(
        val src: String
    )

    data class Billing(
        @SerializedName("email")
        val email: String?,
    )
}


data class Collection(
    @SerializedName("href")
    val href: String?
)

data class Links(
    @SerializedName("collection")
    val collection: List<Collection?>?,
    @SerializedName("self")
    val self: List<Self?>?
)

data class MetaDataX(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("key")
    val key: String?,
    @SerializedName("value")
    val value: String?
)

data class Self(
    @SerializedName("href")
    val href: String?
)

data class Shipping(
    @SerializedName("address_1")
    val address1: String?,
    @SerializedName("address_2")
    val address2: String?,
    @SerializedName("city")
    val city: String?,
    @SerializedName("company")
    val company: String?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("postcode")
    val postcode: String?,
    @SerializedName("state")
    val state: String?
)

data class ShippingLine(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("meta_data")
    val metaData: List<Any?>?,
    @SerializedName("method_id")
    val methodId: String?,
    @SerializedName("method_title")
    val methodTitle: String?,
    @SerializedName("taxes")
    val taxes: List<Any?>?,
    @SerializedName("total")
    val total: String?,
    @SerializedName("total_tax")
    val totalTax: String?
)

data class Refund(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("refund")
    val refund: String?,
    @SerializedName("total")
    val total: String?
)

data class Taxe(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("subtotal")
    val subtotal: String?,
    @SerializedName("total")
    val total: String?
)

data class TaxLine(
    @SerializedName("compound")
    val compound: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("label")
    val label: String?,
    @SerializedName("meta_data")
    val metaData: List<Any?>?,
    @SerializedName("rate_code")
    val rateCode: String?,
    @SerializedName("rate_id")
    val rateId: Int?,
    @SerializedName("shipping_tax_total")
    val shippingTaxTotal: String?,
    @SerializedName("tax_total")
    val taxTotal: String?
)
