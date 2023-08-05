package com.sina.domain.usecase.profile

import android.util.Log
import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.core.model.customer.Customer
import com.sina.core.model.customer.UpdateCustomer
import com.sina.domain.repositroy.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCustomerInfoUseCase @Inject constructor(private val profileRepository: ProfileRepository) :
    UseCaseFlow<UpdateCustomerInfoUseCase.Params, Customer>() {
    data class Params(val customerId: Int, val customer: UpdateCustomer)

    override fun execute(params: Params): Flow<Customer> {

        Log.e("TAG", "execute: before ${params.customer} ${params.customerId}", )
        val updateCustomerInfo =
            profileRepository.updateCustomerInfo(params.customerId, params.customer)
        Log.e("TAG", "execute: after")
        return updateCustomerInfo.safeOpen()
    }
}