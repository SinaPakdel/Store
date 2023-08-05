package com.sina.presentation.review.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sina.core.common.interactor.InteractState
import com.sina.core.local.datastore.AppDataStore
import com.sina.core.model.review.Review
import com.sina.core.network.model.reviewdto.DeleteReviewDto
import com.sina.domain.usecase.review.AddReviewUseCase
import com.sina.domain.usecase.review.GetReviewUseCase
import com.sina.domain.usecase.review.GetReviewsUseCase
import com.sina.domain.usecase.review.RemoveReviewUseCase
import com.sina.domain.usecase.review.UpdateReviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val addReviewUseCase: AddReviewUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val removeReviewUseCase: RemoveReviewUseCase,
    private val updateReviewUseCase: UpdateReviewUseCase,
    private val getReviewUseCase: GetReviewUseCase,
    savedStateHandle: SavedStateHandle,
    private val appDataStore: AppDataStore

) : ViewModel() {
    var productId = ""
    var reviewId = ""

    var customerEmail = ""

    var reviewTitle = ""
    var reviewMessage = ""
    var reviewGoodPoint = ""
    var reviewBadPoint = ""
    var point = 0

    private val _createReviewProduct = MutableStateFlow<InteractState<Review>>(InteractState.Loading)
    val createReviewProduct: StateFlow<InteractState<Review>> = _createReviewProduct.asStateFlow()


    private val _customerReviews = MutableStateFlow<InteractState<List<Review>>>(InteractState.Loading)
    var customerReviews: StateFlow<InteractState<List<Review>>> = _customerReviews.asStateFlow()

    private val _customerReview = MutableStateFlow<InteractState<Review>>(InteractState.Loading)
    var customerReview: StateFlow<InteractState<Review>> = _customerReview.asStateFlow()


    private val _updateReviewProduct = MutableStateFlow<InteractState<Review>>(InteractState.Loading)
    val updateReviewProduct: StateFlow<InteractState<Review>> = _updateReviewProduct.asStateFlow()

    private val _deleteReviewProduct = MutableStateFlow<InteractState<DeleteReviewDto>>(InteractState.Loading)
    val deleteReviewProduct: StateFlow<InteractState<DeleteReviewDto>> = _deleteReviewProduct.asStateFlow()

    init {
        savedStateHandle.get<String>("productId")?.let { productId = it.toString() }
        savedStateHandle.get<String>("reviewId")?.let { reviewId = it.toString() }

        Timber.e("add edit review $productId , $reviewId")
        getCustomerEmail()
        getReviewIfExist()
    }

    private fun getReviewIfExist() {
        if (productId == "null") {
            Timber.e("if")
            getReview()
        } else {
            Timber.e("else")
        }
    }

    /**
     * for ReviewFragment
     */
    fun getCustomerReview(email: String) {
        viewModelScope.launch {
            getReviewsUseCase.invoke(GetReviewsUseCase.Params(email)).collectLatest { result ->
                _customerReviews.emit(result)
            }
        }
    }

    private fun getReview() {
        Timber.e("getReview:$reviewId")
        viewModelScope.launch {
            getReviewUseCase.invoke(GetReviewUseCase.Params(reviewId.toInt())).collectLatest {
                _customerReview.emit(it)
            }
        }
    }

    private fun getCustomerEmail() {
        viewModelScope.launch {
            appDataStore.readCustomerEmail.collectLatest {
                customerEmail = it
                Timber.e("Customer Email:$it")
            }
        }
    }

    fun createReview() {
        Timber.e("productId:$productId")
        if (productId == "null") {
            updateReview(reviewId)
            Timber.e("productId is Empty$productId")
        } else {
            Timber.e("productId not Empty$productId")
            createNewReview(productId)
        }
    }

    private fun updateReview(reviewId: String) {
        viewModelScope.launch {
            updateReviewUseCase.invoke(UpdateReviewUseCase.Params(reviewId.toInt(), createUpdatedReviewObject()))
                .collectLatest {
                    _createReviewProduct.value = it
                }
        }
    }


    private fun createNewReview(productId: String) {
        viewModelScope.launch {
            addReviewUseCase.invoke(AddReviewUseCase.Params(createNewReviewObject())).collectLatest {
                _createReviewProduct.value = it
            }
        }
    }


    fun deleteCustomerReview(reviewId: Int) {
        viewModelScope.launch {
            removeReviewUseCase.invoke(RemoveReviewUseCase.Params(reviewId)).collectLatest {
                _deleteReviewProduct.value = it
            }
        }
    }

    private fun createNewReviewObject(): Review {
        return Review.empty.copy(
            rating = point,
            reviewer_email = customerEmail,
            product_id = productId.toInt(),
            review = reviewMessage
        )
    }

    private fun createUpdatedReviewObject(): Review {
        return Review.empty.copy(
            rating = point,
            reviewer_email = customerEmail,
            review = reviewMessage
        )
    }
}