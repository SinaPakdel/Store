package com.sina.core.model.review

data class CreateReview(
    val product_id: Int,
    val review: String,
    val reviewer: String,
    val reviewer_email: String,
    val rating: Int,
)

data class CreateReviewResponse(
    val date_created: String,
    val id: Int,
    val product_id: Int,
    val rating: Int,
    val review: String,
    val reviewer: String,
    val reviewer_email: String,
)

data class Review(
    val id: Int?,
    val date_created: String,
    val product_id: Int,
    val review: String,
    val reviewer: String,
    val rating: Int,
    val reviewer_email: String,
) {
    companion object {
        val empty = Review(
            id = 0,
            date_created = "",
            0,
            "",
            "",
            0,
            ""
        )
    }
}