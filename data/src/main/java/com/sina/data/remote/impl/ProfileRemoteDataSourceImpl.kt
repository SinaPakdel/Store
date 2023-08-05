package com.sina.data.remote.impl

import com.sina.core.model.customer.UpdateCustomer
import com.sina.core.network.model.customerdto.CustomerDto
import com.sina.data.remote.ProfileRemoteDataSource
import com.sina.core.network.response.openResponse
import com.sina.core.network.services.StoreServices

class ProfileRemoteDataSourceImpl(private val storeServices: StoreServices) :
    ProfileRemoteDataSource {
    override suspend fun updateCustomerInfo(
        customerId: Int,
        customer: UpdateCustomer
    ): CustomerDto =
        storeServices.updateCustomer(customerId, customer).openResponse()

    override suspend fun retrieveCustomer(email: String): List<CustomerDto> =
        storeServices.retrieveCustomer(email).openResponse()
}