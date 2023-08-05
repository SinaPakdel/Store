package com.sina.core.uicomponents.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.sina.core.R
import com.sina.core.databinding.LayoutLottieBinding

class LottieWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
) : FrameLayout(context, attrs) {
    private val binding: LayoutLottieBinding
    private var resource: Int

    init {
        val view = inflate(context, R.layout.layout_lottie, this)
        binding = LayoutLottieBinding.bind(view)

        context.obtainStyledAttributes(attrs, R.styleable.LottieWidget).apply {
            resource = this.getResourceId(R.styleable.LottieWidget_lottie_rawRes, R.raw.response_anim)
            recycle()
        }
        binding.lottie.apply {
            setAnimation(resource)
        }
    }

    fun startLoading(resource: Int = R.raw.response_anim) {
        binding.lottie.apply {
            if (resource != this@LottieWidget.resource) {
                this@LottieWidget.resource = resource
                setAnimation(resource)
            }
            playAnimation()
        }
        this.isVisible = true
    }

    fun stopLoading() {
        this.isVisible = false
        binding.lottie.pauseAnimation()
    }

}