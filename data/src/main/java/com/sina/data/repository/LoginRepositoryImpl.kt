package com.sina.data.repository

import com.sina.core.common.responsestate.ResponseState
import com.sina.core.common.responsestate.asResponse
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.customer.Customer
import com.sina.core.model.mappers.mapCustomerToCustomerDto
import com.sina.core.model.mappers.mapOrderDtoToOrder
import com.sina.core.model.mappers.mapOrderToOrderDto
import com.sina.core.model.order.Order
import com.sina.core.network.model.customerdto.CustomerDto
import com.sina.core.network.model.orderdto.OrderDto
import com.sina.data.remote.LoginRemoteDataSource
import com.sina.domain.repositroy.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginRemoteDataSource: LoginRemoteDataSource,
    private val appDataStore: AppDataStore,
    private val dispatcher: CoroutineDispatcher
) : LoginRepository {
    override fun signInCustomerByEmail(email: String): Flow<ResponseState<Boolean>> = flow {
        var isNewCustomer = false
        val currentCustomer = retrieveCustomer(email)

        /**
         * if customer doesn't exist
         */
        if (currentCustomer.isEmpty()) {
            val createNewCustomer = newCreateCustomer(email)
            newCreateOrder(email)
            Timber.e("${createNewCustomer.id}")
            isNewCustomer = true
            /**
             * if customer exist
             */
        } else {
            Timber.e("else")
            /**
             * check customer has a order id (latest order id)
             */
            val newCheckCustomerOrder = newCheckCustomerOrder(email)
            /**
             * if doesn't have any order id, create new one
             */
            if (newCheckCustomerOrder.isEmpty()) {
                newCreateOrder(email)
            } else {
                /**
                 * check the last order id status is PENDING
                 */
                if (newCheckCustomerOrder.first().status == Order.Status.PENDING.value) {
                    Timber.e("newCheckCustomerOrderId:${newCheckCustomerOrder.first().id}")
                    appDataStore.saveCurrentOrderId(newCheckCustomerOrder.first().id)
                    /**
                     * check the last order id status is not pending, create new order
                     */
                } else {
                    newCreateOrder(email)

                }
            }
            isNewCustomer = true
        }
        Timber.e("signInCustomerByEmail 7")
        appDataStore.saveCustomerEmail(email)
        /**
         * this make customer logged in and change start navigation status
         */
        appDataStore.saveUserLoggedIn(true)

        emit(isNewCustomer)
    }.asResponse().flowOn(dispatcher)

    private suspend fun newCheckCustomerOrder(email: String): List<OrderDto> {
        val retrieveCustomerOrder = loginRemoteDataSource.retrieveCustomerOrder(email)
        Timber.e("retrieveCustomerOrder: ${retrieveCustomerOrder.first().id}")
        return retrieveCustomerOrder
    }

    private suspend fun newCreateOrder(email: String) {
        val order: Order = Order.empty.copy()
        order.billing?.email = email
        val createNewOrder: Order =
            mapOrderDtoToOrder(loginRemoteDataSource.createCustomerOrder(mapOrderToOrderDto(order)))
        Timber.e("create new order :${createNewOrder.orderId}")
        appDataStore.saveCurrentOrderId(createNewOrder.orderId)
    }

    private suspend fun newCreateCustomer(email: String): CustomerDto {
        return loginRemoteDataSource.createCustomer(
            mapCustomerToCustomerDto(
                Customer.empty.copy(
                    email = email
                )
            )
        )
    }


    private suspend fun createCustomer(email: String) {
        loginRemoteDataSource.createCustomer(mapCustomerToCustomerDto(Customer.empty.copy(email = email)))
        createNewOrder(email)
        Timber.e("createCustomer")
    }

    private suspend fun retrieveCustomer(email: String): List<CustomerDto> {
        val customerDto: List<CustomerDto> = loginRemoteDataSource.retrieveCustomer(email)
        appDataStore.saveCustomerId(customerDto.first().id)
//        createNewOrder(email)
        Timber.e("retrieveCustomer $customerDto")
        return customerDto
    }

    private suspend fun checkCustomerOrder(email: String): Boolean {
        val retrieveCustomerOrder: List<Order> = retrieveCustomerOrder(email)
        Timber.e("checkCustomerOrder $retrieveCustomerOrder")
        return retrieveCustomerOrder.isNotEmpty()
    }

    private suspend fun retrieveCustomerOrder(email: String): List<Order> {
        val orders =
            loginRemoteDataSource.retrieveCustomerOrder(email).map { mapOrderDtoToOrder(it) }
        Timber.e("retrieveCustomerOrder$orders")
        return orders

    }

    private suspend fun createNewCustomerOrder(email: String): Order {
        val order: Order = Order.empty.copy()
        order.billing?.email = email
        val createNewOrder: Order =
            mapOrderDtoToOrder(loginRemoteDataSource.createCustomerOrder(mapOrderToOrderDto(order)))
        appDataStore.saveCurrentOrderId(order.orderId)
        Timber.e("createNewCustomerOrder createNewOrder")
        return createNewOrder
    }

    private suspend fun createNewOrder(email: String) {
        appDataStore.saveCurrentOrderId(createNewCustomerOrder(email).orderId)
        Timber.e("createNewOrder")
    }

}