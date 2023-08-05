package com.sina.presentation.category

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.core.R
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.presentation.category.adapter.CategoryAdapter
import com.sina.presentation.databinding.FragmentCategoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate) {
    private val TAG = "CategoryFragment"

    private val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoryAdapter: CategoryAdapter

//    @Inject
//    lateinit var networkListener: NetworkListener
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observes()
    }

//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun setupViews() {
        implRecycler()
        with(binding) {
            rvCategoryItems.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = categoryAdapter
            }
        }
    }

    override fun observers() {
        TODO("Not yet implemented")
    }

    private fun playAnimate() = binding.lottie.lottie.playAnimation()
    private fun cancelAnimate() = binding.lottie.lottie.cancelAnimation()

    private fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            BaseViewModel.UiState.Success -> {
                cancelAnimate()
                false
            }

            BaseViewModel.UiState.Loading -> {
                playAnimate()
                true
            }

            BaseViewModel.UiState.Error -> {
                cancelAnimate()
                false
            }
        }
    }

    private fun implRecycler() {
        categoryAdapter = CategoryAdapter {
            navigateWithArgsTo(R.string.products_deep_link_category, "categoryId", it.toString())
        }
    }

    private fun observes() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.categoryList.collectLatest { categoryAdapter.submitList(it) }
        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.uiState.collectLatest {
                when (it) {
                    BaseViewModel.UiState.Success -> animationStatus(it)
                    BaseViewModel.UiState.Loading -> animationStatus(it)
                    BaseViewModel.UiState.Error -> {
                        animationStatus(it)
                        showToast(it.name)
                    }
                }
            }
        }

        viewLifecycleOwner.launchWhenStarted {
            networkListener.checkNetworkAvailability().collectLatest {
                viewModel.networkStatus = it
                showNetworkStatue()
            }
        }
    }


    private fun showNetworkStatue() {
        if (!viewModel.networkStatus) {
            showToast("No Internet conection")
            viewModel.saveBackOnline(true)
        } else if (viewModel.networkStatus) {
            if (viewModel.backOnline) {
                showToast("Wer back online")
                viewModel.saveBackOnline(false)
            }
        }
    }
}