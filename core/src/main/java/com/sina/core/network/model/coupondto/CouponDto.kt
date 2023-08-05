package com.sina.core.network.model.coupondto

data class CouponDto(
    val code: String,
    val amount: String,
)

data class UpdateCouponDto(
    val status: String = "pending",
    val coupon_lines: List<CouponDto> = emptyList(),
)