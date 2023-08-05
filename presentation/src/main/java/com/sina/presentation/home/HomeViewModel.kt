package com.sina.presentation.home

import androidx.lifecycle.viewModelScope
import com.sina.core.common.filters.Params
import com.sina.core.common.filters.UiText.NEW
import com.sina.core.common.filters.UiText.POPULAR
import com.sina.core.common.filters.UiText.TOP_RATED
import com.sina.core.common.filters.asFilter
import com.sina.core.common.filters.lowerCase
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.productlist.Products
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.domain.usecase.product.ProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    productsUseCase: ProductListUseCase,
//    productsByCategoryUseCase: ProductsByCategoryUseCase,
    private val dataStore: AppDataStore
) : BaseViewModel() {
    private val TAG = "HomeViewModel"
    private val listItem: Array<MainProducts> = Array(3) {
        MainProducts.createData(
            "",
            emptyList()
        )
    }

    val readBackOnline = dataStore.readBackOnline.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )
    private val _allMainProducts = MutableStateFlow<List<MainProducts>>(emptyList())
    val allMainProducts: StateFlow<List<MainProducts>> = _allMainProducts

    private val _sliderImages = MutableStateFlow<List<Products.Image>>(emptyList())
    val sliderImages: StateFlow<List<Products.Image>> = _sliderImages

    private val topRatedProducts: StateFlow<InteractState<List<Products>>> =
        productsUseCase(params = ProductListUseCase.Params(1, applyQueries(TOP_RATED))).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )
    private val latestProducts: StateFlow<InteractState<List<Products>>> =
        productsUseCase(params = ProductListUseCase.Params(1, applyQueries(NEW))).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )
    private val mostProducts: StateFlow<InteractState<List<Products>>> =
        productsUseCase(params = ProductListUseCase.Params(1, applyQueries(POPULAR))).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )

    private val sliderProducts: StateFlow<InteractState<List<Products>>> =
        productsUseCase.invoke(ProductListUseCase.Params(1, applySliderQueries("119"))).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000), InteractState.Loading
        )

    private fun applyQueries(string: String): HashMap<String, String> = hashMapOf<String, String>().apply {
        Timber.e("${string.asFilter().second} , ${string.asFilter().first}")
        put(Params.ORDERBY.lowerCase(), string.asFilter().first)
        put(Params.ORDER.lowerCase(), string.asFilter().second)
    }

    private fun applySliderQueries(string: String) = hashMapOf<String, String>().apply {
        put(Params.CATEGORY.lowerCase(), string)
    }

    private fun StateFlow<InteractState<List<Products>>>.expand(title: String, index: Int) {
        viewModelScope.launch {
            collectLatest {
                when (it) {
                    is InteractState.Error -> {
                        uiState.value = UiState.Error

                        Timber.e("Error ${it.errorMessage}")
                    }

                    is InteractState.Loading -> {
                        uiState.value = UiState.Loading
                    }

                    is InteractState.Success -> {
                        Timber.e("--------->${it.data.size}")
                        listItem[index] = MainProducts.createData(title, it.data)
                        _allMainProducts.value = listItem.toList()
                        uiState.value = UiState.Success
                    }
                }
            }
        }
    }

    fun saveBackOnline(status: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveBackOnline(status)
    }

    init {
        Timber.e("--------------------------------------------->HomeViewModel")
        latestProducts.expand(NEW, 0)
        topRatedProducts.expand(TOP_RATED, 1)
        mostProducts.expand(POPULAR, 2)
        sliderProducts.expand {
            val images = it[0].images
            if (images != null) _sliderImages.value = images
        }
    }

    data class MainProducts(
        val title: String,
        val products: List<Products>,
    ) {
        companion object {
            fun createData(
                title: String,
                products: List<Products>,
            ) = MainProducts(title, products)
        }
    }
}