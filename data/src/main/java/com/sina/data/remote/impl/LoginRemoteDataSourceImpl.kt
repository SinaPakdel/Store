package com.sina.data.remote.impl

import com.sina.core.network.model.customerdto.CustomerDto
import com.sina.core.network.model.orderdto.OrderDto
import com.sina.data.remote.LoginRemoteDataSource
import com.sina.core.network.response.openResponse
import com.sina.core.network.services.StoreServices
import javax.inject.Inject

class LoginRemoteDataSourceImpl @Inject constructor(
    private val storeServices: StoreServices,
) : LoginRemoteDataSource {
    override suspend fun createCustomer(customerDTO: CustomerDto): CustomerDto =
        storeServices.createCustomer(customerDTO).openResponse()

    override suspend fun retrieveCustomer(email: String): List<CustomerDto> =
        storeServices.retrieveCustomer(email).openResponse()

    override suspend fun retrieveCustomerOrder(email: String): List<OrderDto> =
        storeServices.retrieveCustomerOrder(email).openResponse()

    override suspend fun createCustomerOrder(orderDto: OrderDto): OrderDto =
        storeServices.createNewCustomerOrder(orderDto).openResponse()
}