package com.sina.data.remote

import com.sina.core.network.model.coupondto.CouponDto
import com.sina.core.network.model.coupondto.UpdateCouponDto
import com.sina.core.network.model.orderdto.OrderDto
import com.sina.core.network.model.product_list.ProductLstDTOItem

interface CartRemoteDataSource {
    suspend fun getRemoteProductsById(ids: String): List<ProductLstDTOItem>

    suspend fun getCurrentOrder(orderId: Int): OrderDto

    suspend fun updateOrder(orderId: Int, orderDto: OrderDto): OrderDto

    suspend fun validateCoupon(code: String): List<CouponDto>

    suspend fun submitCoupon(orderId: Int,updateCoupon: UpdateCouponDto):OrderDto

}