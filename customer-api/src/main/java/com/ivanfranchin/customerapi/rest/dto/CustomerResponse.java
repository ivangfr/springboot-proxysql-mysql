package com.ivanfranchin.customerapi.rest.dto;

import com.ivanfranchin.customerapi.model.Customer;

public record CustomerResponse(Long id, String firstName, String lastName) {

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName());
    }
}
