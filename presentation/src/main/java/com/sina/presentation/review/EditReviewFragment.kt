package com.sina.presentation.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sina.core.R
import com.sina.core.R.string.bad
import com.sina.core.R.string.good
import com.sina.core.R.string.normal
import com.sina.core.R.string.perfect
import com.sina.core.R.string.very_bad
import com.sina.core.common.interactor.InteractState
import com.sina.core.model.review.Review
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.databinding.FragmentEditReviewBinding
import com.sina.presentation.review.viewmodel.ReviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditReviewFragment : BaseFragment<FragmentEditReviewBinding>(FragmentEditReviewBinding::inflate) {
    private val viewModel: ReviewViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observers()
    }

//    override fun navigateToNetworkFragment() {
//    }

    override fun setupViews() = with(binding) {
        btnSubmit.setOnClickListener {
            collectReviewText()
        }

        sliderReview.addOnChangeListener { _, value, _ ->
            when (value) {
                0.0F -> sliderText.text = ""
                1.0F -> sliderText.text = getString(very_bad)
                2.0F -> sliderText.text = getString(bad)
                3.0F -> sliderText.text = getString(normal)
                4.0F -> sliderText.text = getString(good)
                5.0F -> sliderText.text = getString(perfect)
            }
        }

        collectReviewText()
    }

    private fun FragmentEditReviewBinding.collectReviewText() {
        if (etReviewTitle.text.isNullOrBlank()) showSnack(R.string.pls_input_values)
        else {
            viewModel.reviewTitle = etReviewTitle.text.toString()
            viewModel.reviewGoodPoint = etReviewGoodPoint.text.toString()
            viewModel.reviewBadPoint = etReviewBadPoint.text.toString()
            viewModel.reviewMessage = etProductReview.text.toString()
            viewModel.point = sliderReview.value.toInt()
            viewModel.createReview()
        }
    }

    override fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.createReviewProduct.collectLatest {
                when (it) {
                    is InteractState.Error -> {}
                    is InteractState.Loading -> {}
                    is InteractState.Success -> {
                        showSnack(R.string.string_review_successfully_added)
                        findNavController().popBackStack()
                    }
                }
            }
        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.customerReview.collectLatest {
                when (it) {
                    is InteractState.Error -> {}
                    is InteractState.Loading -> {}
                    is InteractState.Success -> {
                        setUi(it.data)
                    }
                }
            }
        }
    }

    private fun setUi(data: Review) {
        with(binding) {
            etReviewTitle.setText(data.review)
        }
    }
}