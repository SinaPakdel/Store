package com.sina.core.uicomponents.extentions

import android.text.Spanned
import androidx.core.text.HtmlCompat
import java.text.NumberFormat
import java.util.Locale

fun String.fromURI(): Spanned = HtmlCompat.fromHtml(
    this,
    HtmlCompat.FROM_HTML_MODE_LEGACY
)

fun Double.formatPrice(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.US)
    return numberFormat.format(this)
}