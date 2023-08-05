package com.sina.presentation.search.ui.order

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.presentation.databinding.FragmentSearchOrderByBinding
import com.sina.presentation.search.adapter.SearchOrderByAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchOrderByFragment : BaseFragment<FragmentSearchOrderByBinding>(FragmentSearchOrderByBinding::inflate) {
    private lateinit var searchOrderByAdapter: SearchOrderByAdapter
    private val viewModel: SearchOrderByViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

//    override fun navigateToNetworkFragment() {
//        TODO("Not yet implemented")
//    }

    override fun setupViews() {
        searchOrderByAdapter = SearchOrderByAdapter { orderBy ->
            viewModel.saveSearchOrderBy(orderBy)
            findNavController().navigateUp()
        }
        with(binding) {
            searchOrderByAdapter.submitList(viewModel.filterList)
            rvSearchOrders.adapter = searchOrderByAdapter
            rvSearchOrders.layoutManager = LinearLayoutManager(binding.root.context)
        }
    }

    override fun observers() {
        TODO("Not yet implemented")
    }

    fun playAnimate() {
        TODO("Not yet implemented")
    }

    fun cancelAnimate() {
        TODO("Not yet implemented")
    }

    fun animationStatus(state: BaseViewModel.UiState) {

    }
}