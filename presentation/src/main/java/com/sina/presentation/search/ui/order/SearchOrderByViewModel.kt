package com.sina.presentation.search.ui.order

import androidx.lifecycle.viewModelScope
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.search.SearchOrderItem
import com.sina.core.uicomponents.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchOrderByViewModel @Inject constructor(
    private val dataStore: AppDataStore
) : BaseViewModel() {
    fun saveSearchOrderBy(orderTitle: SearchOrderItem) = viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveSearchOrderByType(orderTitle.orderTitle, orderTitle.id)
    }


//    var searchOrderType = Constants.DEFAULT_SEARCH_TYPE

    val filterList = listOf<SearchOrderItem>(
        SearchOrderItem(1, "date"),
        SearchOrderItem(2, "rating"),
        SearchOrderItem(3, "popularity"),
    )

}