package com.sina.presentation.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.core.uicomponents.base.BaseViewModel
import com.sina.presentation.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LoginViewModel by viewModels()
//    override fun navigateToNetworkFragment() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observers()
    }
    override fun setupViews() = with(binding) {
        btnLogin.setOnClickListener {
            Timber.e("btnLogin clicked")
            viewModel.loginCustomer(etEmailCustomer.text.toString())
        }
    }

    override fun observers() {
        launchWhenStarted {
            viewModel.uiState.collectLatest {
                when (it) {
                    BaseViewModel.UiState.Success -> {
                        binding.lottie.lottie.cancelAnimation()
                    }

                    BaseViewModel.UiState.Loading -> {
                        binding.lottie.lottie.playAnimation()
                    }

                    BaseViewModel.UiState.Error -> {
                        binding.lottie.lottie.cancelAnimation()
                    }
                }
            }
        }
    }

}