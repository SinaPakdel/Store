package com.sina.core.network.model.customerdto

import com.google.gson.annotations.SerializedName

data class CustomerDto(
    val id: Int,
    val email: String,
    val username: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val role: String,
    val billing: Billing,
    val shipping: Shipping,
) {
    data class Billing(
        val address_1: String,
        val address_2: String,
        val city: String,
        val country: String,
        val email: String,
        val firstName: String,
        val phone: String,
        val postcode: String,
    )

    data class Shipping(
        val address_1: String,
        val address_2: String,
        val city: String,
        val country: String,
        val firstName: String,
        val postcode: String,
    )
}