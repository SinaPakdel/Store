package com.sina.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sina.core.common.interactor.Success
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.databinding.FragmentEditAddressBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class EditAddressFragment :
    BaseFragment<FragmentEditAddressBinding>(FragmentEditAddressBinding::inflate) {
    private val TAG = "EditAddressFragment"
    private val viewModel: ProfileViewModel by viewModels()

    val args: EditAddressFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

    }

    override fun setupViews() = with(binding) {
        if (args.customerAddress != null)
            etAddressPostal.setText(args.customerAddress.toString())
        btnSubmitAddress.setOnClickListener {
            Log.e(TAG, "setupViews: ")
            viewModel.saveCustomerAddress(etAddressPostal.text.toString())
        }
    }

    override fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.customer.collectLatest {
                if (it.Success) findNavController().popBackStack()
            }
        }
    }
}