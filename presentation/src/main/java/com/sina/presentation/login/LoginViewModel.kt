package com.sina.presentation.login

import androidx.lifecycle.viewModelScope
import com.sina.core.common.interactor.InteractState
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.domain.usecase.login.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    ) : BaseViewModel() {
    private val TAG = "TAG"

    fun loginCustomer(email: String) {
        viewModelScope.launch {
            signInUseCase.invoke(SignInUseCase.Params(email)).collectLatest {
                when (it) {
                    is InteractState.Error -> {

                        uiState.value = UiState.Error
                        Timber.e("loginCustomer Error ${it.errorMessage}")
                    }
                    is InteractState.Loading -> {
                        Timber.e("loginCustomer Loading")

                        uiState.value = UiState.Loading
                    }
                    is InteractState.Success -> {
                        Timber.e("loginCustomer Success")

                        uiState.value = UiState.Success
                    }
                }
            }
        }
    }
}