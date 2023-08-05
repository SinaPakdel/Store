package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.local.model.OrderEntity
import com.sina.core.model.order.Order
import com.sina.core.model.productinfo.ProductInfo
import com.sina.core.network.model.reviewdto.ReviewDto
import kotlinx.coroutines.flow.Flow

interface ProductInfoRepository {
    suspend fun addProductInfoLocal(orderEntity: OrderEntity)
    fun getProductInfoStream(productId: Int): Flow<ResponseState<ProductInfo>>
    fun createProductInfoRemote(order: Order): Flow<ResponseState<Order>>
    fun updateOrder(order: Order): Flow<ResponseState<Order>>
    fun getProductReviewList(productId: Int): Flow<ResponseState<List<ReviewDto>>>
}