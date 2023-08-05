package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.model.customer.Customer
import com.sina.core.model.customer.UpdateCustomer
import com.sina.core.model.mappers.mapCustomerDtoToCustomer
import com.sina.data.remote.ProfileRemoteDataSource
import com.sina.domain.repositroy.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProfileRepositoryImpl(
    private val profileRemoteDataSource: ProfileRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ProfileRepository {

    override fun updateCustomerInfo(
        customerId: Int,
        customer: UpdateCustomer
    ): Flow<ResponseState<Customer>> = flow {
        val customerDto = profileRemoteDataSource.updateCustomerInfo(
            customerId,
            customer
        )
        emit(mapCustomerDtoToCustomer(customerDto))
    }.asResponse().flowOn(ioDispatcher)
}