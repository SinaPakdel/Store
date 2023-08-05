package com.sina.core.model.mappers

import com.sina.core.model.order.Order
import com.sina.core.network.model.orderdto.OrderDto

fun mapOrderToOrderDto(order: Order): OrderDto = with(order) {
    val lineItemsDto = line_items?.map {
        OrderDto.LineItem(
            it.lineItemId,
            it.name,
            it.price,
            it.product_id,
            it.quantity,
            it.total,
            OrderDto.Image(it.image.src)
        )
    } ?: emptyList()

    OrderDto(
        orderId,
        status?.value,
        total,
        lineItemsDto,
        "",
        billing?.let {
            OrderDto.Billing(it.email)
        },
        discountTotal,
    )
}

fun mapOrderDtoToOrder(orderDto: OrderDto): Order = with(orderDto) {
    val lineItems = orderDto.lineItems.map { lineItemDto ->
        Order.LineItem(
            lineItemDto.id,
            lineItemDto.productId,
            lineItemDto.name ?: "",
            lineItemDto.total ?: "",
            lineItemDto.price ?: 0.0,
            lineItemDto.quantity,
            Order.Image(lineItemDto.image.src)
        )
    }

    Order(
        orderDto.id.toInt(),
        Order.Status.values().find { it.value == status } ?: Order.Status.UNKNOWN,
        lineItems,
        billing?.let { Order.OrderBilling(it.email) },
        total,
        discountTotal = discount_total?:""
    )
}
