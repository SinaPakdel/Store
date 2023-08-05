package com.sina.data.local

import com.sina.core.local.model.OrderEntity

interface CartLocalDataSource {
    suspend fun deleteOrderLocal(orderEntity: OrderEntity)

    suspend fun updateOrderLocal(orderEntity: OrderEntity)

    fun getListOrdersLocal(): List<OrderEntity>
}