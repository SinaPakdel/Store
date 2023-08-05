package com.sina.data.local

import com.sina.core.local.model.OrderEntity

interface ProductInfoLocalDataSource {
    suspend fun addOrderLocal(orderEntity: OrderEntity)
}