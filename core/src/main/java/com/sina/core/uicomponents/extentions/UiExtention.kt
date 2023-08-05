package com.sina.core.uicomponents.extentions

import android.view.View

fun View.gone() { visibility = View.GONE }
fun View.visible() { visibility = View.VISIBLE }
fun View.invisible() { visibility = View.INVISIBLE }
fun View.isVisible(): Boolean = visibility == View.VISIBLE
fun View.isInVisible(): Boolean = visibility == View.INVISIBLE