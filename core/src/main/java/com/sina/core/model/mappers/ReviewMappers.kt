package com.sina.core.model.mappers

import com.sina.core.model.review.Review
import com.sina.core.network.model.reviewdto.ReviewDto

fun mapReviewDtoToReview(params: ReviewDto): Review = with(params) {
    Review(
        id, date_created, product_id, review, reviewer, rating, reviewer_email
    )
}

fun mapReviewToReviewDto(params: Review): ReviewDto = with(params) {
    ReviewDto(
        date_created,
        null,
        product_id = product_id,
        rating,
        review = review,
        reviewer,
        reviewer_email,
        null
    )
}