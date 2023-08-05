package com.sina.core.model.coupon

data class Coupon(
    val amount: String,
    val code: String,
)
data class UpdateCoupon(
    val status: String = "pending",
    val couponLines: List<Coupon> = emptyList()
)
