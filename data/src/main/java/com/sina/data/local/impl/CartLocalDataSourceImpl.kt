package com.sina.data.local.impl

import com.sina.core.local.db.OrderDao
import com.sina.core.local.model.OrderEntity
import com.sina.data.local.CartLocalDataSource

class CartLocalDataSourceImpl(private val orderDao: OrderDao) : CartLocalDataSource {
    override suspend fun deleteOrderLocal(orderEntity: OrderEntity) {
        orderDao.deleteOrderLocal(orderEntity)
    }

    override suspend fun updateOrderLocal(orderEntity: OrderEntity) {
        orderDao.updateOrderLocal(orderEntity)
    }

    override fun getListOrdersLocal(): List<OrderEntity> {
        return orderDao.getListOrderLocal()
    }
}