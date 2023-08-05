package com.sina.presentation.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.sina.core.R
import com.sina.core.common.interactor.InteractState
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.databinding.FragmentReviewBinding
import com.sina.presentation.review.adapter.ReviewAdapter
import com.sina.presentation.review.viewmodel.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class ReviewFragment : BaseFragment<FragmentReviewBinding>(FragmentReviewBinding::inflate) {

    private val viewModel: ReviewViewModel by viewModels()
    lateinit var reviewAdapter: ReviewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.e("ReviewFragment")
        setupViews()
        viewModel.getCustomerReview(viewModel.customerEmail)
        observers()
    }

//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun setupViews() = with(binding) {
        reviewAdapter = ReviewAdapter(onclick = {
            // TODO: navigate to review fragment
        }, onDeleteClick = { reviewId, position ->
            viewModel.deleteCustomerReview(reviewId)
            reviewAdapter.notifyItemRemoved(position)
        }, onEditClick = {
            navigateWithArgsTo(R.string.edit_review_deep_link, "productId", "null", "reviewId", it.toString())
            // TODO: navigate to editReviewFragment
        })
        rvProfileReview.adapter = reviewAdapter

    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.customerReviews.collect {
                        when (it) {
                            is InteractState.Error -> {}
                            is InteractState.Loading -> {}
                            is InteractState.Success -> {
                                reviewAdapter.submitList(it.data)
                            }
                        }
                    }
                }
                launch {
                    viewModel.deleteReviewProduct.collect {
                        when (it) {
                            is InteractState.Error -> {}
                            is InteractState.Loading -> {}
                            is InteractState.Success -> {
                                viewModel.getCustomerReview(viewModel.customerEmail)
                            }
                        }
                    }
                }
            }
        }
    }
}