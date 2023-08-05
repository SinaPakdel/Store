package com.sina.core.uicomponents.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color.*
import android.graphics.Rect
import android.text.method.PasswordTransformationMethod
import android.text.method.TransformationMethod
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.sina.core.R
import com.sina.core.databinding.WidgetTextInputLayoutBinding

@SuppressLint("CustomViewStyleable")
class TextInputLayoutWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
) : FrameLayout(context, attrs) {
    private lateinit var baseColor: ColorStateList
    private val binding: WidgetTextInputLayoutBinding

    init {
        val view = inflate(context, R.layout.widget_text_input_layout, this)
        binding = WidgetTextInputLayoutBinding.bind(view)

        val attributes = context.obtainStyledAttributes(attrs, R.styleable.widget_text_input_layout)
        initView(attributes)
        attributes.recycle()
    }

    private fun initView(attributes: TypedArray) = with(binding) {
        textInputLayoutRoot.apply {
            hint = attributes.getString(R.styleable.widget_text_input_layout_layout_hint) ?: ""
            val hasPassword =
                attributes.getBoolean(R.styleable.widget_text_input_layout_hasPassword, false)
            if (hasPassword) {
                setupPasswordToggle()
            }
            setHelperTextColor(ColorStateList.valueOf(RED))
            baseColor = textInputLayoutText.textColors
        }
    }

    private fun setupPasswordToggle() = with(binding) {
        textInputLayoutRoot.apply {
            startIconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_eye)
            var isVisible = true
            setStartIconOnClickListener {
                textInputLayoutText.transformationMethod = if (isVisible) {
                    PasswordTransformationMethod.getInstance()
                } else {
                    object : TransformationMethod {
                        override fun getTransformation(p0: CharSequence, p1: View?): CharSequence {
                            return p0
                        }

                        override fun onFocusChanged(
                            p0: View?,
                            p1: CharSequence?,
                            p2: Boolean,
                            p3: Int,
                            p4: Rect?
                        ) {
                        }
                    }
                }
                setStartIconTintList(ColorStateList.valueOf(if (isVisible) BLACK else WHITE))
                isVisible = isVisible.not()
            }
            this.callOnClick()
        }
    }

    val text: String
        get() = binding.textInputLayoutText.text.toString()

    fun clear() = with(binding) {
        textInputLayoutText.apply {
            setText("")
            error = null
        }
        textInputLayoutRoot.apply {
            helperText = ""
        }
    }

    fun check(predicate: (String) -> Boolean): Boolean {
        return check() && predicate(text).also {
            binding.textInputLayoutText.apply {
                if (it) {
                    setTextColor(baseColor)
                } else {
                    setTextColor(RED)
                }
            }
        }
    }

    fun check(): Boolean = with(binding) {
        textInputLayoutText.setText(text.trim())
        return if (text.isNotBlank()) {
            textInputLayoutRoot.helperText = null
            true
        } else {
            textInputLayoutRoot.helperText = context.getString(R.string.required_message)
            false
        }
    }

}