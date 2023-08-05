package com.sina.domain.repositroy

import com.sina.core.common.responsestate.ResponseState
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun signInCustomerByEmail(email: String): Flow<ResponseState<Boolean>>
}