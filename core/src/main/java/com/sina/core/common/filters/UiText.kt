package com.sina.core.common.filters

import com.sina.core.common.filters.Order.DESC
import com.sina.core.common.filters.OrderBy.*
import com.sina.core.common.filters.UiText.NEW
import com.sina.core.common.filters.UiText.POPULAR
import com.sina.core.common.filters.UiText.TOP_RATED

object UiText {
    const val NEW = "جدید ترین ها"
    const val OLD = "قدیمی ترین ها"
    const val TITLE_A_Z = "حروف الفبا (آ-ی)"
    const val TITLE_Z_A = "حروف الفبا (ی-آ)"
    const val EXPENSIVE = "گران ترین ها"
    const val INEXPENSIVE = "ارزان ترین ها"
    const val POPULAR = "محبوب ترین ها"
    const val HATED = "منفور ترین ها"
    const val TOP_RATED = "پر امتیاز ترین ها"
    const val LOW_RATED = "کم امتیاز ترین ها"
    const val SLIDER = "119"
}

enum class Params {
    ORDERBY, ORDER, CATEGORY
}

enum class OrderBy {
    ID, DATE, INCLUDE, TITLE, SLUG, PRICE, POPULARITY, RATING
}

enum class Order {
    DESC, ASC
}

fun Params.lowerCase() = this.name.lowercase()


fun String.asFilter() = when (this) {
    NEW -> Pair(DATE.lowerCase(), DESC.lowerCase())
    TOP_RATED -> Pair(RATING.lowerCase(), DESC.lowerCase())
    POPULAR -> Pair(POPULARITY.lowerCase(), DESC.lowerCase())
    else -> Pair(ID.name.lowercase(), DESC.name.lowercase())
}

fun Order.lowerCase() = this.name.lowercase()
fun OrderBy.lowerCase() = this.name.lowercase()
