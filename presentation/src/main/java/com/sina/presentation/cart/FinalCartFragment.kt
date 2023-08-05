package com.sina.presentation.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sina.core.R
import com.sina.core.common.interactor.InteractState
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.core.uicomponents.extentions.formatPrice
import com.sina.core.uicomponents.extentions.invisible
import com.sina.core.uicomponents.extentions.isVisible
import com.sina.core.uicomponents.extentions.visible
import com.sina.presentation.databinding.FragmentFinalCartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class FinalCartFragment : BaseFragment<FragmentFinalCartBinding>(FragmentFinalCartBinding::inflate) {


    private val viewModel: CartViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observers()
    }

//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun setupViews() = with(binding) {
        materialTextView.setOnClickListener {
            // TODO:  
        }

        btnSetCoupon.setOnClickListener {
            if (showSetCoupon.isVisible()) {
                showSetCoupon.invisible()
                btnSetCoupon.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_arrow_left,
                    0,
                    0,
                    0
                )
            } else {
                showSetCoupon.visible()
                btnSetCoupon.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_arrow_up,
                    0,
                    0,
                    0
                )
            }
        }

        submitCoupon.setOnClickListener {
            if (etAddCoupon.text.isNullOrEmpty()) showSnack(R.string.string_input_error)
            else viewModel.submitCoupon(etAddCoupon.text.toString())
        }
    }

    override fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.listOfOrders.collectLatest {
                when (it) {
                    is InteractState.Error -> {Timber.e("${it.errorMessage}")}
                    is InteractState.Loading -> {Timber.e("Loading")}
                    is InteractState.Success -> {
                        Timber.e("FinalCartFragment:${it.data}")
                        binding.tvTotalPrice.text = it.data.total.toDouble().formatPrice()
                    }
                }
            }
        }
    }

}