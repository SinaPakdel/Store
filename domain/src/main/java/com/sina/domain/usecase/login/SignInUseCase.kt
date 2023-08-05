package com.sina.domain.usecase.login

import com.sina.core.common.interactor.UseCaseFlow
import com.sina.core.common.responsestate.safeOpen
import com.sina.domain.repositroy.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInUseCase@Inject constructor(
    private val loginRepository: LoginRepository,
) : UseCaseFlow<SignInUseCase.Params, Boolean>() {
    data class Params(val email: String)

    override fun execute(params: Params): Flow<Boolean> = with(params) {
        loginRepository.signInCustomerByEmail(email).safeOpen()
    }
}