package com.ivanfranchin.customerapi.mapper;

import com.ivanfranchin.customerapi.model.Customer;
import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.CustomerResponse;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;

public interface CustomerMapper {

    CustomerResponse toCustomerResponse(Customer customer);

    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    void updateCustomerFromRequest(UpdateCustomerRequest updateCustomerRequest, Customer customer);
}
