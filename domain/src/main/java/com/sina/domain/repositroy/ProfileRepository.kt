package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.model.customer.Customer
import com.sina.core.model.customer.UpdateCustomer
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun updateCustomerInfo(customerId: Int, customer: UpdateCustomer): Flow<ResponseState<Customer>>
}