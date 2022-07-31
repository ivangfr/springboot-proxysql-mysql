package com.ivanfranchin.customerapi.service;

import com.ivanfranchin.customerapi.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer validateAndGetCustomer(Long id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Customer customer);
}
