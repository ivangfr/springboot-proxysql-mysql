package com.ivanfranchin.customerapi.customer.dto;

import com.ivanfranchin.customerapi.customer.model.Customer;

public record CustomerResponse(Long id, String firstName, String lastName) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName());
    }
}
