package com.sina.presentation.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.core.R
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.presentation.databinding.FragmentHomeBinding
import com.sina.presentation.home.adapter.HomeSliderAdapter
import com.sina.presentation.home.adapter.MainHomeAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private lateinit var navController: NavController

//    @Inject
//    lateinit var networkListener: NetworkListener

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mainHomeAdapter: MainHomeAdapter
    private lateinit var homeSliderAdapter: HomeSliderAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observers()
    }

//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun setupViews() {
        navController = findNavController()
        implRecyclerView()
        with(binding) {
            searchLayer.btnSearchHome.setOnClickListener {
                navigateTo(R.string.search_deep_link)
//                startActivity(Intent(requireActivity(), SearchActivity::class.java))
            }
            rvSliderHome.apply {
                adapter = homeSliderAdapter
                set3DItem(true)
                setInfinite(true)
            }
            rvMainProducts.apply {
                adapter = mainHomeAdapter
                val linearLayoutManager = LinearLayoutManager(binding.root.context)
                linearLayoutManager.reverseLayout = true
                layoutManager = linearLayoutManager


            }
        }
    }

    private fun playAnimate() = binding.lottie.lottie.playAnimation()
    private fun cancelAnimate() = binding.lottie.lottie.cancelAnimation()
    private fun implRecyclerView() {
        mainHomeAdapter = MainHomeAdapter(onClick = {
            showDetails(it)
        }, onMoreClick = { moreString ->
            Timber.e("$$$$$$$$$$moreString")
            showMoreProducts(moreString)
        })
        homeSliderAdapter = HomeSliderAdapter()
    }


    private fun showDetails(it: Int) {
        navigateWithArgsTo(R.string.details_deep_link, "productId", it.toString())

//        findNavController().navigate(
//            NavDeepLinkRequest.Builder.fromUri(
//                Uri.parse(
//                    getString(R.string.details_deep_link).replace(
//                        "{id}", "$it"
//                    )
//                )
//            ).build()
//        )
//        Intent(requireActivity(), ItemActivity::class.java).apply {
//            putExtra("productId", it)
//        }.also { startActivity(it) }
    }

    private fun showMoreProducts(moreString: String) {
//        Intent(requireContext(), ProductsActivity::class.java).apply {
//            putExtra("orderBy", moreString)
//        }.also { startActivity(it) }
    }

    override fun observers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.allMainProducts.collectLatest {
                    mainHomeAdapter.submitList(it)
                }
            }
        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.sliderImages.collectLatest { homeSliderAdapter.submitList(it) }

        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.uiState.collectLatest {
                animationStatus(it)
            }
        }

        viewLifecycleOwner.launchWhenStarted {
            networkListener.checkNetworkAvailability().collectLatest {
                viewModel.networkStatus = it
                showNetworkStatue()
            }
        }
        viewLifecycleOwner.launchWhenStarted {
            viewModel.readBackOnline.collectLatest {
                viewModel.backOnline = it
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

    fun animationStatus(state: BaseViewModel.UiState) {
        binding.lottie.lottie.isVisible = when (state) {
            BaseViewModel.UiState.Success -> {
                cancelAnimate();false
            }

            BaseViewModel.UiState.Loading -> {
                playAnimate();true
            }

            BaseViewModel.UiState.Error -> {
                cancelAnimate();false
            }
        }
    }
}