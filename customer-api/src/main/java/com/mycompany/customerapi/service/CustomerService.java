package com.mycompany.customerapi.service;

import com.mycompany.customerapi.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer validateAndGetCustomer(Long id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Customer customer);

}
