package com.sina.presentation.cart

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.core.R
import com.sina.core.common.interactor.InteractState
import com.sina.core.network.services.StoreServices
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.core.uicomponents.extentions.formatPrice
import com.sina.presentation.cart.adapter.CartAdapter
import com.sina.presentation.databinding.FragmentCartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>(FragmentCartBinding::inflate) {



    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    var totalPrice = 0.0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.getCurrentOrderRemote()
        observers()
    }

//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun setupViews() = with(binding) {
        cartAdapter = CartAdapter(

            itemClick = {
                navigateWithArgsTo(R.string.details_deep_link, "productId", it.toString())
            },
            itemQuantity = { lineItemId, lineItemQuantity ->
                viewModel.updateCurrentProduct(lineItemQuantity, lineItemId)
            })
        rvCartProducts.layoutManager = LinearLayoutManager(root.context)
        rvCartProducts.adapter = cartAdapter

        btnSubmitCart.setOnClickListener {
            navigateTo(R.string.final_cart_deep_link)
        }
    }


    override fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.listOfProducts.collect {
                when (it) {
                    is InteractState.Error -> {}
                    is InteractState.Loading -> {}
                    is InteractState.Success -> {
                        binding.lottie.lottie.visibility = View.GONE
                        cartAdapter.submitList(it.data.line_items)
                        Log.e("TAG", "observers: ${it.data}")
                        binding.tvTotalPrice.text = it.data.discountTotal.toDouble().formatPrice()
                    }
                }
            }
        }
    }
}