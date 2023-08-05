package com.sina.presentation.productlsit

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.databinding.FragmentProductListBinding
import com.sina.presentation.productlsit.adapter.ProductsAdapter
import com.sina.core.R
import com.sina.core.uicomponents.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>(FragmentProductListBinding::inflate) {
    override fun setupViews() {
        productsAdapter = ProductsAdapter {
            navigateWithArgsTo(R.string.details_deep_link, "productId", it.toString())
//            val intent = Intent(requireActivity(), ItemActivity::class.java)
//            intent.putExtra("productId", it)
//            startActivity(intent)
        }
        with(binding) {
            rvProducts.apply {
                adapter = productsAdapter
                layoutManager = LinearLayoutManager(binding.root.context)
            }


        }
    }

    fun playAnimate() {
        binding.lottie.lottie.playAnimation()
    }

    fun cancelAnimate() {
        binding.lottie.lottie.cancelAnimation()
    }

    fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            BaseViewModel.UiState.Success -> {
                cancelAnimate();false
            }

            BaseViewModel.UiState.Loading -> {
                playAnimate()
                true
            }

            BaseViewModel.UiState.Error -> {
                cancelAnimate();false
            }
        }
    }

    private val viewModel: ProductListViewModel by viewModels()
    private lateinit var productsAdapter: ProductsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("TAG", "onViewCreated: ")
        observers()
        setupViews()
        setPaging()
    }

//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.products.collectLatest {
                Log.e("TAG", "observers: $it")
                productsAdapter.submitList(it.toMutableList())
            }
        }

//        viewLifecycleOwner.launchWhenStarted {
//            viewModel.uiState.collectLatest {
//                when (it) {
//                    Success -> animationStatus(it)
//                    Loading -> animationStatus(it)
//                    Error -> animationStatus(it)
//                }
//            }
//        }
    }

    private fun setPaging() {
        binding.rvProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = linearLayoutManager.findLastVisibleItemPosition()
                if (position == productsAdapter.itemCount - 1) {
                    viewModel.nextPage()
                }
            }
        })
    }
}