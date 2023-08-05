package com.sina.data.remote

import com.sina.core.network.model.customerdto.CustomerDto
import com.sina.core.network.model.orderdto.OrderDto

interface LoginRemoteDataSource {
    suspend fun createCustomer(customerDTO: CustomerDto): CustomerDto
    suspend fun retrieveCustomer(email: String): List<CustomerDto>
    suspend fun retrieveCustomerOrder(email: String): List<OrderDto>
    suspend fun createCustomerOrder(orderDto: OrderDto): OrderDto

}