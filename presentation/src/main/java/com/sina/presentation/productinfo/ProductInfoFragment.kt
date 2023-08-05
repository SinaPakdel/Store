package com.sina.presentation.productinfo

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import com.sina.core.R
import com.sina.core.common.interactor.InteractState
import com.sina.core.model.productinfo.ProductInfo
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.core.uicomponents.base.BaseViewModel.*
import com.sina.core.uicomponents.extentions.fromURI
import com.sina.presentation.databinding.FragmentProductInfoBinding
import com.sina.presentation.productinfo.adapter.ImageDetailsAdapter
import com.sina.presentation.productinfo.adapter.ReviewProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber


@AndroidEntryPoint
class ProductInfoFragment : BaseFragment<FragmentProductInfoBinding>(FragmentProductInfoBinding::inflate) {
    private val TAG = "ProductFragment"

    private val viewModel: ProductInfoViewModel by viewModels()
    private lateinit var imageDetailsAdapter: ImageDetailsAdapter
    private lateinit var reviewProductAdapter: ReviewProductAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        imageDetailsAdapter = ImageDetailsAdapter()
        reviewProductAdapter = ReviewProductAdapter({})
        setupViews()
        observers()
    }

    override fun setupViews() {
        with(binding) {
            rvItemImages.adapter = imageDetailsAdapter
            rvProductReviews.adapter = reviewProductAdapter
            btnCreateReview.setOnClickListener {
                navigateWithArgsTo(R.string.edit_review_deep_link, "productId", viewModel.productId.toString(),"reviewId","null")
            }
        }
    }

//    private fun playAnimate() = binding.lottie.lottie.playAnimation()
//    private fun cancelAnimate() = binding.lottie.lottie.cancelAnimation()
//
//    private fun animationStatus(state: BaseViewModel.UiState) {
//        binding.lottie.lottie.isVisible = when (state) {
//            Success -> {
//                cancelAnimate();
//                viewStatus(true)
//                false
//            }
//
//            Loading -> {
//                playAnimate();
//                viewStatus(false)
//                true
//            }
//
//            Error -> {
//                cancelAnimate();
//                viewStatus(false)
//                false
//            }
//        }
//    }

//    private fun viewStatus(state: Boolean) {
//        binding.btnAddToCart.isVisible = state
//    }


//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.productDetails.collectLatest {
                when (it) {
                    is InteractState.Error -> {
                        binding.loadingWidget.onFail(it.errorMessage.toString())
                        Timber.d(it.errorMessage)
                        viewModel.uiState.value = UiState.Error
                    }

                    is InteractState.Loading -> binding.loadingWidget.onLoading()
                    is InteractState.Success -> {
                        binding.loadingWidget.onSuccess()
                        implUi(it.data)
                    }
                }
            }
        }

        viewLifecycleOwner.launchWhenStarted {
            viewModel.productReviewList.collectLatest {
                when (it) {
                    is InteractState.Error -> {
                        Log.e(TAG, "observers: ${it.errorMessage}")
                    }

                    is InteractState.Loading -> {
                        Log.e(TAG, "observers: loading")
                    }

                    is InteractState.Success -> {
                        reviewProductAdapter.submitList(it.data)
                    }
                }
            }
        }

//        viewLifecycleOwner.launchWhenStarted {
//            viewModel.uiState.collectLatest {
//                animationStatus(it)
//            }
//        }

        viewLifecycleOwner.launchWhenStarted {
            viewModel.itemAdded.collectLatest {
                when (it) {
                    true -> {
                        binding.btnAddToCart.text = getString(R.string.string_go_to_cart)
                    }

                    false -> {
                        binding.btnAddToCart.text = getString(R.string.string_buy)
                    }
                }
            }
        }

    }

    private fun implUi(data: ProductInfo) {
        with(binding) {
            imageDetailsAdapter.submitList(data.images)
            tvItemDescription.text = HtmlCompat.fromHtml(
                data.description.toString(),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            tvItemDescription.text = data.description.toString().fromURI()
            tvItemPrice.text = data.price
            tvItemTitle.text = data.name
            btnAddToCart.setOnClickListener {
//                viewModel.addItemToCart(data.id)
                if (btnAddToCart.text == getString(R.string.string_go_to_cart)) navigateTo(R.string.cart_deep_link) else
                    viewModel.updateOrder()
            }
            tvProductRate.text = data.ratingCount.toString()
        }
    }
}