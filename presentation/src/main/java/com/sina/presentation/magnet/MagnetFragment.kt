package com.sina.presentation.magnet

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sina.presentation.R

class MagnetFragment : Fragment() {

    companion object {
        fun newInstance() = MagnetFragment()
    }

    private lateinit var viewModel: MagnetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_magnet, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MagnetViewModel::class.java)
        // TODO: Use the ViewModel
    }

}