package com.ivanfranchin.customerapi.mapper;

import com.ivanfranchin.customerapi.model.Customer;
import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.CustomerResponse;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public CustomerResponse toCustomerResponse(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponse(customer.getId(), customer.getFirstName(), customer.getLastName());
    }

    @Override
    public Customer toCustomer(CreateCustomerRequest createCustomerRequest) {
        if (createCustomerRequest == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setFirstName(createCustomerRequest.firstName());
        customer.setLastName(createCustomerRequest.lastName());
        return customer;
    }

    @Override
    public void updateCustomerFromRequest(UpdateCustomerRequest updateCustomerRequest, Customer customer) {
        if (updateCustomerRequest != null) {
            if (updateCustomerRequest.firstName() != null) {
                customer.setFirstName(updateCustomerRequest.firstName());
            }
            if (updateCustomerRequest.lastName() != null) {
                customer.setLastName(updateCustomerRequest.lastName());
            }
        }
    }
}
