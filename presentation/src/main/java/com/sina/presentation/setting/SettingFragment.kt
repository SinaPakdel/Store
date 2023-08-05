package com.sina.presentation.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.sina.core.uicomponents.base.BaseFragment
import com.sina.presentation.R
import com.sina.presentation.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>(FragmentSettingBinding::inflate) {
    private val viewModel by viewModels<SettingViewModel>()

//    override fun navigateToNetworkFragment() {
//
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun setupViews() = with(binding) {
        radioGroupDefaultTime.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                com.sina.presentation.R.id.three -> {
                    tvTimeNotification.text = buildString {
                        append(3)
                        append(getString(com.sina.core.R.string.string_hours))
                    }
                    viewModel.setTime(3)
                }

                R.id.five -> {
                    tvTimeNotification.text = buildString {
                        append(5)
                        append(getString(com.sina.core.R.string.string_hours))
                    }
                    viewModel.setTime(5)
                }

                R.id.eight -> {
                    tvTimeNotification.text = buildString {
                        append(8)
                        append(getString(com.sina.core.R.string.string_hours))
                    }
                    viewModel.setTime(8)
                }

                R.id.twelve -> {
                    tvTimeNotification.text = buildString {
                        append(12)
                        append(getString(com.sina.core.R.string.string_hours))
                    }
                    viewModel.setTime(12)
                }
            }
        }

        btnSettingInfo.setOnClickListener {
            Log.e("TAG", "setupViews: ")
            if (showSettingInfo.visibility == View.VISIBLE) {
//                TransitionManager.beginDelayedTransition(binding.cardViewSetting, AutoTransition())
                showSettingInfo.visibility = View.GONE
                btnSettingInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    com.sina.core.R.drawable.ic_arrow_left,
                    0,
                    0,
                    0
                )
            } else {
//                TransitionManager.beginDelayedTransition(binding.cardViewSetting, AutoTransition())
                showSettingInfo.visibility = View.VISIBLE
                btnSettingInfo.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    com.sina.core.R.drawable.ic_arrow_up,
                    0,
                    0,
                    0
                )
            }
        }

        sliderCustomTime.addOnChangeListener { _, value, _ ->
            viewModel.setTime(value.toInt())
            value.toString().also {
                tvTimeNotification.text = buildString {
                    append(getString(com.sina.core.R.string.string_each))
                    append(it.substringBefore(".").toInt())
                    append(getString(com.sina.core.R.string.string_hours))
                    append(" ")
                    append(getString(com.sina.core.R.string.string_per))
                }
                viewModel.currentTime = it
            }
            Timber.e("$value")
        }

        toggleButton.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnToggleActivateNotification -> {
                        viewModel.startService()
                    }

                    R.id.btnToggleDeActivateNotification -> {
                        viewModel.stopService()
                    }
                }
            }
        }

        btnSettingLogout.setOnClickListener {
            viewModel.userLoggedOut()
            findNavController().popBackStack()
        }
    }

    override fun observers() {}
}