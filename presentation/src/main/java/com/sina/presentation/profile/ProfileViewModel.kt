package com.sina.presentation.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.customer.Customer
import com.sina.core.model.customer.UpdateCustomer
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.domain.usecase.profile.UpdateCustomerInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateCustomerInfoUseCase: UpdateCustomerInfoUseCase,
    private val appDataStore: AppDataStore
) : BaseViewModel() {
    var customerEmail = ""
    var customerId = -1

    private val _customer = MutableStateFlow<InteractState<Customer>>(InteractState.Loading)
    val customer: StateFlow<InteractState<Customer>> = _customer

    init {
        getCustomerEmail()
    }

    fun getCustomerEmail() {
        viewModelScope.launch {
            appDataStore.readCustomerEmail.collectLatest {
                Timber.e("Customer Email:$it")
                customerEmail = it
            }
        }
        viewModelScope.launch {
            appDataStore.customerId.collectLatest {
                Timber.e("Customer Id:$it")
                customerId = it
            }
        }
    }

    fun saveCustomerAddress(customerAddress: String) {
        viewModelScope.launch {
            Log.e("TAG", "saveCustomerAddress: $customerId $customerEmail")
            updateCustomerInfoUseCase.invoke(
                UpdateCustomerInfoUseCase.Params(
                    customerId,
                    UpdateCustomer(shipping = UpdateCustomer.Shipping(customerAddress))
                )
            ).collectLatest {
                _customer.emit(it)
            }
        }
    }
}