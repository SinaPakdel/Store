package com.sina.core.uicomponents.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding>(private val inflate: (LayoutInflater) -> VB) : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding: VB get() = _binding ?: throw IllegalStateException("View binding has not been initialized yet.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setupViews()
//    abstract fun animationStatus(state: BaseViewModel.UiState)
}