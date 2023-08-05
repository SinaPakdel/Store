package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.local.model.OrderEntity
import com.sina.core.model.order.Order
import com.sina.core.model.productlist.Products
import com.sina.core.network.model.coupondto.CouponDto
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun deleteOrderLocal(orderEntity: OrderEntity)

    suspend fun updateOrderLocal(orderEntity: OrderEntity)

    suspend fun deleteOrderRemote(order: Order)

    fun updateOrderRemote(order: Order): Flow<ResponseState<Order>>

    fun getListOfProductOfCart(orderId: Int): Flow<ResponseState<List<Products>>>

    fun getListOfOrderLocal(): Flow<ResponseState<List<OrderEntity>>>

    fun getCurrentNewOrder(orderId: Int): Flow<ResponseState<Order>>

    suspend fun validateCoupon(code: String): List<CouponDto>

    fun submitCoupon(code: String,orderId: Int): Flow<ResponseState<Order>>
}