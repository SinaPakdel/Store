package com.sina.data.remote

import com.sina.core.model.customer.UpdateCustomer
import com.sina.core.network.model.customerdto.CustomerDto

interface ProfileRemoteDataSource {
    suspend fun updateCustomerInfo(customerId: Int, customer: UpdateCustomer): CustomerDto
    suspend fun retrieveCustomer(email: String): List<CustomerDto>
}