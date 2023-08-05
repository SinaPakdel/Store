package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.local.model.OrderEntity
import com.sina.core.model.mappers.mapOrderDtoToOrder
import com.sina.core.model.mappers.mapOrderToOrderDto
import com.sina.core.model.order.Order
import com.sina.core.model.productlist.Products
import com.sina.core.model.productlist.mapListOfProductDtoToProduct
import com.sina.core.network.model.coupondto.CouponDto
import com.sina.core.network.model.coupondto.UpdateCouponDto
import com.sina.data.local.CartLocalDataSource
import com.sina.data.remote.CartRemoteDataSource
import com.sina.domain.repositroy.CartRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class CartRepositoryImpl(
    private val cartLocalDataSource: CartLocalDataSource,
    private val cartRemoteDataSource: CartRemoteDataSource,
    private val dispatcher: CoroutineDispatcher,

    ) : CartRepository {
    override fun getListOfOrderLocal(): Flow<ResponseState<List<OrderEntity>>> = flow {
        emit(cartLocalDataSource.getListOrdersLocal())
    }.asResponse().flowOn(dispatcher)

    override suspend fun deleteOrderLocal(orderEntity: OrderEntity) =
        cartLocalDataSource.deleteOrderLocal(orderEntity)

    override suspend fun updateOrderLocal(orderEntity: OrderEntity) =
        cartLocalDataSource.updateOrderLocal(orderEntity)

    override suspend fun deleteOrderRemote(order: Order) {
        TODO("Not yet implemented")
    }

    override fun updateOrderRemote(order: Order): Flow<ResponseState<Order>> = flow {
        val orderDto = cartRemoteDataSource.updateOrder(order.orderId, mapOrderToOrderDto(order))
        emit(mapOrderDtoToOrder(orderDto))
    }.asResponse().flowOn(dispatcher)

    private suspend fun getProductsOfCartByIdRemote(ids: List<Int>): List<Products> =
        mapListOfProductDtoToProduct(cartRemoteDataSource.getRemoteProductsById(ids.joinToString(",")))

    private suspend fun getCurrentOrder(orderId: Int): Order =
        mapOrderDtoToOrder(cartRemoteDataSource.getCurrentOrder(orderId))


    override fun getListOfProductOfCart(orderId: Int): Flow<ResponseState<List<Products>>> = flow {
        val listOfProductsIds = mutableListOf<Int>()
        getCurrentOrder(orderId).line_items?.forEach { it.product_id?.let { id -> listOfProductsIds.add(id) } }
        emit(getProductsOfCartByIdRemote(listOfProductsIds))
    }.asResponse().flowOn(dispatcher)

    /**
     * This is new way to get orders
     */
    override fun getCurrentNewOrder(orderId: Int): Flow<ResponseState<Order>> = flow {
        val currentOrder = mapOrderDtoToOrder(cartRemoteDataSource.getCurrentOrder(orderId))
        emit(currentOrder)
    }.asResponse().flowOn(dispatcher)

    override suspend fun validateCoupon(code: String): List<CouponDto> {
        val coupon = cartRemoteDataSource.validateCoupon(code)
        Timber.e("validateCoupon code:${coupon.first().code},amount:${coupon.first().amount}")
        Timber.e("coupon:$coupon")
        return coupon
    }

    override fun submitCoupon(code: String, orderId: Int): Flow<ResponseState<Order>> = flow {
        val submitCoupon = cartRemoteDataSource.submitCoupon(orderId, UpdateCouponDto(coupon_lines = validateCoupon(code)))
        Timber.e("submitCoupon:$submitCoupon")
        emit(mapOrderDtoToOrder(submitCoupon))
    }.asResponse().flowOn(dispatcher)
}