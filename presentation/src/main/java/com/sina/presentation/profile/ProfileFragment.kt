package com.sina.presentation.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.sina.core.R
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.getCustomerEmail()
    }

    override fun setupViews() = with(binding) {


        btnProfileSetting.setOnClickListener {
            navigateTo(R.string.setting_deep_link)
        }
        btnCustomerReviews.setOnClickListener {
            navigateTo(R.string.review_deep_link)
        }
        btnCustomerAddress.setOnClickListener {
            navigateTo(R.string.address_deep_link)
        }

        tvProfileEmail.text = viewModel.customerEmail
    }

    override fun observers() {

    }
}