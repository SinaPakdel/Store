package com.sina.core.model.mappers

import com.sina.core.model.coupon.Coupon
import com.sina.core.model.coupon.UpdateCoupon
import com.sina.core.network.model.coupondto.CouponDto
import com.sina.core.network.model.coupondto.UpdateCouponDto

fun mapUpdateCouponDtoToUpdateCoupon(dto: UpdateCouponDto): UpdateCoupon {
    val coupons = dto.coupon_lines.map { Coupon(it.amount, it.code) }
    return UpdateCoupon(dto.status, coupons)
}
fun mapUpdateCouponToUpdateCouponDto(coupon: UpdateCoupon): UpdateCouponDto {
    val couponDto = coupon.couponLines.map { CouponDto(it.amount, it.code) }
    return UpdateCouponDto(coupon.status, couponDto)
}