package com.sina.data.remote

import com.sina.core.network.model.orderdto.OrderDto
import com.sina.core.network.model.productinfodto.ProductInfoDto
import com.sina.core.network.model.reviewdto.ReviewDto

interface ProductInfoRemoteDataSource {
    suspend fun getProductInfoRemote(productId: Int): ProductInfoDto

    suspend fun createOrderRemote(orderDto: OrderDto): OrderDto

    suspend fun updateOrder(orderId: Int, orderDto: OrderDto): OrderDto

    suspend fun getProductReviewList(productId: Int): List<ReviewDto>



}