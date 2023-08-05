package com.sina.core.model.customer

data class Customer(
    val id: Int,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
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

    companion object {
        val empty = Customer(
            0, "", "", "", "", "", "",
            Billing("", "", "", "", "", "", "", ""),
            Shipping("", "", "", "", "", "")
        )
    }
}