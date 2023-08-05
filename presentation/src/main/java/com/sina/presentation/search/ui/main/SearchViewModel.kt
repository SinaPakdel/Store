package com.sina.presentation.search.ui.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sina.core.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_BY_TYPE
import com.sina.core.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_BY_TYPE_ID
import com.sina.core.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_TYPE
import com.sina.core.common.constants.Constants.Companion.DEFAULT_SEARCH_ORDER_TYPE_ID
import com.sina.core.common.constants.Constants.Companion.ORDER
import com.sina.core.common.constants.Constants.Companion.ORDER_BY
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.productlist.Products
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.domain.usecase.search.GetSearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchProductsUseCase: GetSearchProductsUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dataStore: AppDataStore
) : BaseViewModel() {

    var searchOrderType = DEFAULT_SEARCH_ORDER_TYPE
    var searchOrderTypeId = DEFAULT_SEARCH_ORDER_TYPE_ID
    var searchOrderByType = DEFAULT_SEARCH_ORDER_BY_TYPE
    var searchOrderByTypeId = DEFAULT_SEARCH_ORDER_BY_TYPE_ID


    private val _productsBySearch = MutableStateFlow<List<Products>>(mutableListOf())
    val productsBySearch: StateFlow<List<Products>> = _productsBySearch.asStateFlow()
    private var job: Job? = null
    private var page = 1
    private var searchQuery: String = ""


    val readSearchType = dataStore.readSearchType
    fun nextPage() {
        page++
        getProductsBySearch(searchQuery)
    }

    fun applyQueriesQueries() = hashMapOf<String, String>().apply {
        viewModelScope.launch {
            readSearchType.collect { values ->
                searchOrderType = values.selectedSearchOrderType
                searchOrderTypeId = values.selectedSearchOrderTypeId
                Timber.e("$searchOrderByType ,$searchOrderByTypeId ")
                searchOrderByType = values.selectedSearchOrderByType
                searchOrderByTypeId = values.selectedSearchOrderByTypeId
            }
        }
        Timber.e("Filters:-->searchQuery:$searchQuery,searchOrderByType:$searchOrderByType,searchOrderType:$searchOrderType")
//        put(SEARCH, searchQuery)
        put(ORDER_BY, searchOrderByType)
        put(ORDER, searchOrderType)
    }

    fun getProductsBySearch(searchQuery: String) {
        this.searchQuery = searchQuery
        job?.cancel()
        job = viewModelScope.launch {
            Log.e("TAG", "getProductsBySearch: $page")
            getSearchProductsUseCase.invoke(
                GetSearchProductsUseCase.Params(
                    page,
                    searchQuery,
                    applyQueriesQueries()
                )
            ).collectLatest {
                when (it) {
                    is InteractState.Error -> {
                        Timber.d(it.errorMessage)
                        uiState.value = UiState.Error
                    }

                    is InteractState.Loading -> uiState.value = UiState.Loading
                    is InteractState.Success -> {
                        uiState.value = UiState.Success
                        _productsBySearch.value += it.data
                    }
                }
            }
        }
    }

    fun saveSearchOrderType(searchOrderTypeChip: String, searchOrderTypeIdChip: Int) =
        viewModelScope.launch {
            Timber.e("Search chip group:::::::$searchOrderTypeChip,$searchOrderTypeIdChip")
            dataStore.saveSearchOrderType(searchOrderTypeChip, searchOrderTypeIdChip)
        }
}