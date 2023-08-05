package com.sina.presentation.productinfo

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.order.Order
import com.sina.core.network.model.orderdto.OrderDto
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.domain.usecase.product.AddProductInfoLocalUseCase
import com.sina.domain.usecase.product.AddProductInfoRemoteUseCase
import com.sina.domain.usecase.product.GetProductInfoUseCase
import com.sina.domain.usecase.product.GetProductReviewList
import com.sina.domain.usecase.product.UpdateOrderRemoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class ProductInfoViewModel @Inject constructor(
    private val getProductInfoUseCase: GetProductInfoUseCase,
    private val addProductInfoLocalUseCase: AddProductInfoLocalUseCase,
    private val addProductInfoRemoteUseCase: AddProductInfoRemoteUseCase,
    private val updateOrderRemoteUseCase: UpdateOrderRemoteUseCase,
    private val getListOfProductReviews: GetProductReviewList,
    savedStateHandle: SavedStateHandle,
    private val dataStore: AppDataStore,
) : BaseViewModel() {
    var productId = 1
    private val TAG = "ProductInfoViewModel"
    private var currentOrderId = -1
    private var customerEmail = ""
    private var reviewId = 1

    init {
        savedStateHandle.get<Int>("productId")?.let { productId = it }

//        val id = savedStateHandle.get<Int>("productId")
//        if (id != null) itemId = id
        Timber.e("Init:currentProductId::: $productId", TAG)
        getCurrentOrderId()
        getCustomerEmail()

    }

    private val _itemAdded = MutableStateFlow<Boolean>(false)
    val itemAdded: StateFlow<Boolean> = _itemAdded.asStateFlow()


    private val _createOrder = MutableStateFlow<InteractState<List<OrderDto>>>(InteractState.Loading)
    val createOrder: StateFlow<InteractState<List<OrderDto>>> = _createOrder.asStateFlow()


    val readBackOnline = dataStore.readBackOnline.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )


    /**
     * local
     */
    fun addItemToCart(id: Int) {
        _itemAdded.value = true
        // TODO: check this id later
        viewModelScope.launch {
            addProductInfoLocalUseCase.invoke(AddProductInfoLocalUseCase.Params(productId = id, 1))
        }
    }

    val productDetails =
        getProductInfoUseCase(GetProductInfoUseCase.Params(productId)).stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )

    val productReviewList = getListOfProductReviews.invoke(GetProductReviewList.Params(productId)).stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        InteractState.Loading
    )

    fun updateOrder() {
        _itemAdded.value = true
        viewModelScope.launch {
            updateOrderRemoteUseCase(UpdateOrderRemoteUseCase.Params(createCurrentOrder(1, productId))).collect() {
                when (it) {
                    is InteractState.Error -> Log.e(TAG, "createOrder: Error${it.errorMessage}")
                    is InteractState.Loading -> Log.e(TAG, "createOrder: Loading")
                    is InteractState.Success -> Log.e(TAG, "createOrder: Success ${it.data}")
                }
            }
        }
    }

    private fun createCurrentOrder(itemQuantity: Int, itemId: Int): Order {
        Timber.e("createCurrentOrder,currentProductId: $itemId", TAG)
        Timber.e("createCurrentOrder,currentProductId: $currentOrderId", TAG)
        val lineItems = mutableListOf(
            Order.LineItem(
                lineItemId = null,
                product_id = itemId,
                quantity = itemQuantity,
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
            total = "",""
        )
    }
//    private fun createCurrentOrder(): Order {
//        Timber.e("createCurrentOrder,currentProductId: $productId", TAG)
//        Timber.e("createCurrentOrder,currentProductId: $currentOrderId", TAG)
//        val lineItems =
//            mutableListOf(Order.LineItem(product_id = productId, quantity = 1))
//        return Order(currentOrderId, null, lineItems, Order.OrderBilling(customerEmail))
//    }

    private fun getCustomerEmail() {
        viewModelScope.launch {
            dataStore.readCustomerEmail.collectLatest {
                customerEmail = it
                Timber.e("getCustomerEmail,customerEmail :$it", TAG)
            }
        }
    }

    private fun getCurrentOrderId() {
        viewModelScope.launch {
            dataStore.readCurrentOrderId.collectLatest {
                currentOrderId = it
                Timber.e("getCurrentOrderId,currentOrderId: $it", TAG)
            }
        }
    }
}