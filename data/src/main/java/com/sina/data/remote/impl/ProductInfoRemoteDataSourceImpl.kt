package com.sina.data.remote.impl

import com.sina.core.network.model.orderdto.OrderDto
import com.sina.core.network.model.productinfodto.ProductInfoDto
import com.sina.core.network.model.reviewdto.ReviewDto
import com.sina.data.remote.ProductInfoRemoteDataSource
import com.sina.core.network.response.openResponse
import com.sina.core.network.services.StoreServices

class ProductInfoRemoteDataSourceImpl(private val storeServices: StoreServices) :
    ProductInfoRemoteDataSource {
//    override suspend fun getProductInfoRemote(productId: Int): ProductInfo =
//        mapProductDetailsDtoToProductDetails(storeServices.getProductInfo(productId))

    override suspend fun getProductInfoRemote(productId: Int): ProductInfoDto =
        storeServices.getProductInfo(productId).openResponse()

    override suspend fun createOrderRemote(orderDto: OrderDto): OrderDto =
        storeServices.createOrder(orderDto).openResponse()

    override suspend fun updateOrder(orderId: Int, orderDto: OrderDto): OrderDto =
        storeServices.updateOrder(orderId, orderDto).openResponse()

    override suspend fun getProductReviewList(productId: Int): List<ReviewDto> =
        storeServices.getReviewsProduct(productId).openResponse()
}