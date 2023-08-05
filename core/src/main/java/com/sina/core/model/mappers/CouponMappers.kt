package com.sina.core.model.mappers

import com.sina.core.model.coupon.Coupon
import com.sina.core.network.model.coupondto.CouponDto

fun mapCouponDtoToCoupon(params: CouponDto): Coupon = with(params) {
    return Coupon(
        amount,
        code
    )
}

fun mapCouponToCouponDto(params: Coupon): CouponDto = with(params) {
    return CouponDto(
        amount,
        code
    )
}
