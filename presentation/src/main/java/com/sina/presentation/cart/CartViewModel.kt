package com.sina.presentation.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.order.Order
import com.sina.domain.usecase.cart.GetCurrentNewOrderUseCase
import com.sina.domain.usecase.cart.SubmitCouponUseCase
import com.sina.domain.usecase.cart.UpdateOrderRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val updateOrderRemoteUseCase: UpdateOrderRemoteUseCase,
//    private val getProductCart: GetProductCart,
    private val getCurrentNewOrderUseCase: GetCurrentNewOrderUseCase,
    private val submitCouponUseCase: SubmitCouponUseCase,
    private val appDateStore: AppDataStore
) : ViewModel() {
    private val TAG = "CartViewModel"
    private var currentOrderId = -1
    private var customerEmail = ""

    private val _listOfProducts = MutableStateFlow<InteractState<Order>>(InteractState.Loading)
    val listOfProducts = _listOfProducts.asStateFlow()

    private val _listOfOrders = MutableStateFlow<InteractState<Order>>(InteractState.Loading)
    val listOfOrders = _listOfOrders.asStateFlow()

    init {
        getCurrentOrderId()
        getCustomerEmail()
    }

    fun getCurrentOrderRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            appDateStore.readCurrentOrderId.collectLatest {
                getCurrentNewOrderUseCase.invoke(GetCurrentNewOrderUseCase.Params(it)).collect { interactState ->
                    _listOfProducts.emit(interactState)
                }
            }
        }
    }

    private fun createCurrentOrder(lineItemQuantity: Int, lineItemId: Int): Order {
        Timber.e("createCurrentOrder,lineItemId: $lineItemId", TAG)
        Timber.e("createCurrentOrder,currentOrderId: $currentOrderId", TAG)
        Timber.e("createCurrentOrder,lineItemQuantity: $lineItemQuantity", TAG)
        val lineItems = mutableListOf(
            Order.LineItem(
                lineItemId = lineItemId,
                product_id = null,
                quantity = lineItemQuantity,
                name = "",
                total = "",
                price = null,
                image = Order.Image("")
            )
        )

        return Order(
            orderId = currentOrderId,
            status = null,
            line_items = lineItems,
            billing = Order.OrderBilling(email = customerEmail),
            total = "",
            discountTotal = ""
        )
    }

    private fun getCurrentOrderId() {
        viewModelScope.launch {
            appDateStore.readCurrentOrderId.collectLatest {
                currentOrderId = it
                Timber.e("getCurrentOrderId,currentOrderId: $it", TAG)
            }
        }
    }

    fun updateCurrentProduct(lineItemQuantity: Int, lineItemId: Int) {
        val currentOrder = createCurrentOrder(lineItemQuantity, lineItemId)
        viewModelScope.launch {
            updateOrderRemoteUseCase(UpdateOrderRemoteUseCase.Params(currentOrder)).collect() {
                _listOfProducts.emit(it)
            }
        }
    }

    private fun getCustomerEmail() {
        viewModelScope.launch {
            appDateStore.readCustomerEmail.collectLatest {
                customerEmail = it
                Timber.e("getCustomerEmail,customerEmail :$it", TAG)
            }
        }
    }


    fun submitCoupon(coupon: String) {
        viewModelScope.launch {
            submitCouponUseCase.invoke(SubmitCouponUseCase.Params(coupon, currentOrderId)).collectLatest {
                _listOfOrders.emit(it)
            }
        }
    }

}