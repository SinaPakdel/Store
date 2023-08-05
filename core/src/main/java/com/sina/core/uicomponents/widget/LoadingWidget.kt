package com.sina.core.uicomponents.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.LinearLayoutCompat
import com.airbnb.lottie.LottieDrawable
import com.sina.core.R
import com.sina.core.databinding.WidgetLoadingBinding
import com.sina.core.uicomponents.extentions.gone
import com.sina.core.uicomponents.extentions.visible

class LoadingWidget constructor(
    context: Context,
    attrs: AttributeSet
) : LinearLayoutCompat(context, attrs) {

    private var binding: WidgetLoadingBinding

    init {
        val view = inflate(context, R.layout.widget_loading, this)
        binding = WidgetLoadingBinding.bind(view)
    }

    fun onLoading() = with(binding) {
        lottie.visible()
        lottie.playAnimation()
        lottie.repeatCount = LottieDrawable.INFINITE
        error.gone()
        reload.gone()

    }

    fun onSuccess() {
        binding.apply {
            lottie.gone()
            lottie.pauseAnimation()
            error.gone()
            reload.gone()
        }
    }

    fun onFail(input: String) {
        binding.apply {
            lottie.gone()
            lottie.pauseAnimation()
            error.visible()
            reload.visible()
            error.text = input
        }
    }

    fun click(fn: () -> Unit) {
        binding.reload.setOnClickListener {
            fn()
        }
    }
}