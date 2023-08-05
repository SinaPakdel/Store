package com.sina.data.remote.impl

import com.sina.core.network.model.coupondto.CouponDto
import com.sina.core.network.model.coupondto.UpdateCouponDto
import com.sina.core.network.model.orderdto.OrderDto
import com.sina.core.network.model.product_list.ProductLstDTOItem
import com.sina.data.remote.CartRemoteDataSource
import com.sina.core.network.response.openResponse
import com.sina.core.network.services.StoreServices

class CartRemoteDataSourceImpl(private val storeServices: StoreServices) : CartRemoteDataSource {
    override suspend fun getRemoteProductsById(ids: String): List<ProductLstDTOItem> =
        storeServices.getListOfOrderCart(ids).openResponse()

    override suspend fun getCurrentOrder(orderId: Int): OrderDto =
        storeServices.getCurrentOrder(orderId).openResponse()

    override suspend fun updateOrder(orderId: Int, orderDto: OrderDto): OrderDto =
        storeServices.updateOrder(orderId, orderDto).openResponse()

    override suspend fun validateCoupon(code: String): List<CouponDto> =
        storeServices.validateCoupon(code).openResponse()

    override suspend fun submitCoupon(orderId: Int, updateCoupon: UpdateCouponDto): OrderDto =
        storeServices.submitCoupon(orderId, updateCoupon).openResponse()

}