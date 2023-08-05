package com.sina.core.network.model.reviewdto

data class ReviewDto(
    val date_created: String,
    val id: Int?,
    val product_id: Int,
    val rating: Int,
    val review: String,
    val reviewer: String,
    val reviewer_email: String,
    val verified: Boolean?
)