package com.sina.core.model.customer

data class UpdateCustomer(
    val shipping:Shipping,
){
    data class Shipping(
        val address_1: String,
    )
}