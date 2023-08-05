package com.sina.data.local.impl

import com.sina.core.local.db.OrderDao
import com.sina.core.local.model.OrderEntity
import com.sina.data.local.ProductInfoLocalDataSource

class ProductInfoLocalDataSourceImpl(private val orderDao: OrderDao) : ProductInfoLocalDataSource {
    override suspend fun addOrderLocal(orderEntity: OrderEntity) =
        orderDao.insertOrderLocal(orderEntity)
}