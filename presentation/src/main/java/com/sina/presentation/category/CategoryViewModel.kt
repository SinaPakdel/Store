package com.sina.presentation.category

import androidx.lifecycle.viewModelScope
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.category.Category
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.domain.usecase.category.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase,
    private val dataStore: AppDataStore
) :
    BaseViewModel() {
    private val TAG = "CategoryViewModel"

    val readBackOnline = dataStore.readBackOnline.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )
    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList.asStateFlow()

    private val categoriseProductsParams = CategoryUseCase.Params(1, "")

    private val categoriseProductsList: StateFlow<InteractState<List<Category>>> =
        categoryUseCase(categoriseProductsParams).stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5_000),
            InteractState.Loading
        )

    fun saveBackOnline(status: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        dataStore.saveBackOnline(status)
    }

    init {
        categoriseProductsList.expand {
            _categoryList.value = it
        }
    }
}