package com.sina.presentation.profile

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sina.core.R.drawable.ic_arrow_left
import com.sina.core.common.interactor.InteractState
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.R
import com.sina.presentation.databinding.FragmentAddressBinding
import com.sina.presentation.profile.adapter.AddressAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AddressFragment : BaseFragment<FragmentAddressBinding>(FragmentAddressBinding::inflate) {
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()


    }

    override fun setupViews() = with(binding) {
        headerWidget.setText("آدرسها")
        headerWidget.setLeftIcon(ic_arrow_left) {
            Log.e("TAG", "setupViews: ")
        }
        btnAddAddress.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_additAddressFragment)
        }
    }

    override fun observers() {
        viewLifecycleOwner.launchWhenStarted {
            viewModel.customer.collectLatest {
                when (it) {
                    is InteractState.Error ->{}
                    is InteractState.Loading ->{}
                    is InteractState.Success ->{
                        binding.tvAddressCity.text=it.data.shipping.address_1
                        binding.tvAddressCity.text=it.data.billing.address_1
                    }
                }
            }
        }
    }
}