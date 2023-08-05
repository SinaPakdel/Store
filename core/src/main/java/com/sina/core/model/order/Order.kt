package com.sina.core.model.order


data class Order(
    val orderId: Int = -1,
    val status: Status?,
    val line_items: List<LineItem>?,
    val billing: OrderBilling?,
    val total: String,
    val discountTotal: String,
) {
    data class LineItem(
        val lineItemId: Int? = -1,
        val product_id: Int?,
        val name: String,
        val total: String,
        val price: Double?,
        val quantity: Int?,
        val image: Image
    )

    data class Image(
        val src: String
    )

    data class OrderBilling(
        var email: String?,
    )

    companion object {
        val empty = Order(
            orderId = 0,
            line_items = emptyList(),
            status = null,
            billing = OrderBilling(""),
            total = "",
            discountTotal = ""
        )
    }

    enum class Status(val value: String) {
        PENDING("pending"),
        PROCESSING("processing"),
        ON_HOLD("on-hold"),
        COMPLETED("completed"),
        CANCELLED("cancelled"),
        FAILED("failed"),
        UNKNOWN("unknown"),

    }
}