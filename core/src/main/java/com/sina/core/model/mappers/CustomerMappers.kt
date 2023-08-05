package com.sina.core.model.mappers

import com.sina.core.local.model.CustomerEntity
import com.sina.core.model.customer.Customer
import com.sina.core.network.model.customerdto.CustomerDto


fun mapCustomerToCustomerEntity(customer: Customer): CustomerEntity = with(customer) {
    CustomerEntity(id, email, username, firstName, lastName, avatarUrl, role)
}

//fun mapCustomerEntityToCustomer(customerEntity: CustomerEntity): Customer = with(customerEntity) {
//    Customer(
//        id, email, username, firstName, lastName, avatarUrl, role,
//        CustomerDto.Billing("", "", "", "", "", "", "", ""),
//        CustomerDto.Shipping("", "", "", "", "", "")
//    )
//}

fun mapCustomerDtoToCustomerEntity(customerDTO: CustomerDto): CustomerEntity = with(customerDTO) {
    CustomerEntity(id, email, username, firstName, lastName, avatarUrl, role)
}

fun mapCustomerDtoToCustomer(dto: CustomerDto): Customer = with(dto) {
    Customer(
        id, email, username, firstName, lastName, avatarUrl, role,
        Customer.Billing(
            billing.address_1,
            billing.address_2,
            billing.city,
            billing.country,
            billing.email,
            billing.firstName,
            billing.phone,
            billing.postcode
        ),
        Customer.Shipping(
            shipping.address_1,
            shipping.address_2,
            shipping.city,
            shipping.country,
            shipping.firstName,
            shipping.postcode
        )
    )
}

fun mapCustomerToCustomerDto(customer: Customer): CustomerDto = with(customer) {
    CustomerDto(
        id, email, username, firstName, lastName, avatarUrl, role,
        CustomerDto.Billing(
            billing.address_1,
            billing.address_2,
            billing.city,
            billing.country,
            billing.email,
            billing.firstName,
            billing.phone,
            billing.postcode
        ),
        CustomerDto.Shipping(
            shipping.address_1,
            shipping.address_2,
            shipping.city,
            shipping.country,
            shipping.firstName,
            shipping.postcode
        )
    )
}