package com.sina.presentation.search.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sina.presentation.R

class SearchFilterFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFilterFragment()
    }

    private lateinit var viewModel: SearchFilterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_filter, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchFilterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}