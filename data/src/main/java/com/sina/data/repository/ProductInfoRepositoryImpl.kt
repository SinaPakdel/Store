package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.local.model.OrderEntity
import com.sina.core.model.mappers.mapOrderDtoToOrder
import com.sina.core.model.mappers.mapOrderToOrderDto
import com.sina.core.model.mappers.mapProductInfoDtoToProductInfo
import com.sina.core.model.order.Order
import com.sina.core.model.productinfo.ProductInfo
import com.sina.core.network.model.reviewdto.ReviewDto
import com.sina.data.local.ProductInfoLocalDataSource
import com.sina.data.remote.ProductInfoRemoteDataSource
import com.sina.domain.repositroy.ProductInfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductInfoRepositoryImpl(
    private val productInfoRemoteDataSource: ProductInfoRemoteDataSource,
    private val productInfoLocalDataSource: ProductInfoLocalDataSource,
    private val dispatcher: CoroutineDispatcher,
) : ProductInfoRepository {
    override fun getProductInfoStream(productId: Int): Flow<ResponseState<ProductInfo>> = flow {
        emit(
            mapProductInfoDtoToProductInfo(
                productInfoRemoteDataSource.getProductInfoRemote(
                    productId
                )
            )
        )
    }.asResponse().flowOn(dispatcher)

    override fun createProductInfoRemote(order: Order): Flow<ResponseState<Order>> = flow {
        val orderDto = productInfoRemoteDataSource.createOrderRemote(mapOrderToOrderDto(order))
        emit(mapOrderDtoToOrder(orderDto))
    }.asResponse().flowOn(dispatcher)

    override fun updateOrder(order: Order): Flow<ResponseState<Order>> = flow {
        val orderDto =
            productInfoRemoteDataSource.updateOrder(order.orderId, mapOrderToOrderDto(order))
        emit(mapOrderDtoToOrder(orderDto))
    }.asResponse().flowOn(dispatcher)

    override fun getProductReviewList(productId: Int): Flow<ResponseState<List<ReviewDto>>> = flow {
        emit(productInfoRemoteDataSource.getProductReviewList(productId))
    }.asResponse().flowOn(dispatcher)

    override suspend fun addProductInfoLocal(orderEntity: OrderEntity) =
        productInfoLocalDataSource.addOrderLocal(orderEntity)
}