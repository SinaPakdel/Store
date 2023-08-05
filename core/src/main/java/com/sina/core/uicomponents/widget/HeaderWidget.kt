package com.sina.core.uicomponents.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.sina.core.R
import com.sina.core.databinding.WidgetTopHeaderBinding

class HeaderWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var binding: WidgetTopHeaderBinding


    init {
        val view = inflate(context, R.layout.widget_top_header, this)
        binding = WidgetTopHeaderBinding.bind(view)
    }

    /**
     * Set the text of the TextView
     */
    fun setText(text: String) = with(binding) {
        tvTitle.text = text
    }

    /**
     * Set the left icon of the TextView
     *
     * @param icon The drawable to set as the left icon
     * @param listener The listener to set for the left icon
     */
    fun setLeftIcon(icon: Int?, listener: ((View) -> Unit)? = null) = with(binding) {
        icon?.let { imgLeftArrow.setImageResource(it) }
        if (listener != null) imgLeftArrow.setOnClickListener(listener)
        imgLeftArrow.visibility = if (icon != null) View.VISIBLE else View.GONE
    }

    /**
     * Set the right icon of the TextView
     *
     * @param icon The drawable to set as the right icon
     * @param listener The listener to set for the right icon
     */
    fun setRightIcon(icon: Int?, listener: ((View) -> Unit)? = null) = with(binding) {
        icon?.let { imgRightArrow.setImageResource(it) }
        if (listener != null) imgRightArrow.setOnClickListener(listener)
        imgRightArrow.visibility = if (icon != null) View.VISIBLE else View.GONE
    }

    /**
     * Set the content of the TextView, including text and icons
     *
     * @param text The text to set for the TextView
     * @param leftIcon The drawable to set as the left icon
     * @param rightIcon The drawable to set as the right icon
     */
    fun setContent(text: String, leftIcon: Int? = null, rightIcon: Int? = null) {
        setText(text)
        setLeftIcon(leftIcon)
        setRightIcon(rightIcon)
    }
}