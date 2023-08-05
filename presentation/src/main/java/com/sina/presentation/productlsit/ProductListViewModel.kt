package com.sina.presentation.productlsit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sina.core.common.filters.Params
import com.sina.core.common.filters.asFilter
import com.sina.core.common.filters.lowerCase
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.productlist.Products
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.domain.usecase.product.GetProductsByCategoryUseCase
import com.sina.domain.usecase.product.GetProductsByOrderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    savedStateHandle: SavedStateHandle,
    getProductsByOrderUseCase: GetProductsByOrderUseCase,
    dataStore: AppDataStore
) : BaseViewModel() {
    private val productsItems = arrayListOf<Products>()
    private var categoryId = ""
    private var orderBy = ""
    private var page = 1

    init {
        Log.e("TAG", "golabi: ")
        Timber.e("golabi")
        val getCategoryId = savedStateHandle.get<Int>("categoryId")
        val getOrderBy = savedStateHandle.get<String>("orderBy")
        Timber.e("----------------------->$getCategoryId")
        Log.e("TAG", "$getCategoryId: ")
        if (getCategoryId != null) {
            categoryId = getCategoryId.toString()
            getProductsByCategory()
        }
        if (getOrderBy != null) {
            orderBy = getOrderBy
            getProductsByOrder()
        }
    }

    private val _products = MutableStateFlow<List<Products>>(emptyList())
    val products: StateFlow<List<Products>> = _products.asStateFlow()

    private val productsByCategory: StateFlow<InteractState<List<Products>>> =
        getProductsByCategoryUseCase(GetProductsByCategoryUseCase.Params(page, applyQueriesByCategory(categoryId)))
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading)

    private val productsByOrder: StateFlow<InteractState<List<Products>>> =
        getProductsByOrderUseCase.invoke(GetProductsByOrderUseCase.Params(page, applyQueriesByOrder(orderBy)))
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), InteractState.Loading)

    private fun applyQueriesByCategory(string: String) = hashMapOf<String, String>().apply {
        put(Params.CATEGORY.lowerCase(), string)
    }

    private fun applyQueriesByOrder(string: String): HashMap<String, String> = hashMapOf<String, String>().apply {
        Timber.e("${string.asFilter().second} , ${string.asFilter().first}")
        put(Params.ORDER.lowerCase(), string.asFilter().second)
        put(Params.ORDERBY.lowerCase(), string.asFilter().first)
    }

    fun nextPage() {
        page++
    }


    private fun getProductsByOrder() {
        viewModelScope.launch {
            productsByOrder.expand {
                addProductsItems(it)
                _products.value = it
            }
        }
    }

    private fun getProductsByCategory() {
        viewModelScope.launch {
            productsByCategory.expand {
                addProductsItems(it)
                _products.value = it
            }
        }
    }

//    private fun getProducts(categoryId: String) {
//        viewModelScope.launch {
//            productsByCategoryUseCase(ProductsByCategoryUseCase.Params(page, categoryId)).collect {
//                when (it) {
//                    is InteractState.Error -> {
//                        uiState.value = UiState.Error
//                        Log.e("TAG", "getProducts: ")
//                    }
//
//                    is InteractState.Loading -> {
//                        uiState.value = UiState.Loading
//                        Log.e("TAG", "getProducts: ")
//                    }
//
//                    is InteractState.Success -> {
//                        uiState.value = UiState.Success
//                        addProductsItems(it.data)
//                        _products.value = productsItems
//                    }
//                }
//            }
//        }
//    }

    private fun addProductsItems(data: List<Products>) {
        Log.e("TAG", "addProductsItems: ${productsItems.size}")
        productsItems.addAll(data)
    }
}