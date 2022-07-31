package com.ivanfranchin.customerapi.rest;

import com.ivanfranchin.customerapi.rest.dto.CustomerResponse;
import com.ivanfranchin.customerapi.mapper.CustomerMapper;
import com.ivanfranchin.customerapi.model.Customer;
import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;
import com.ivanfranchin.customerapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public List<CustomerResponse> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomer(id);
        return customerMapper.toCustomerResponse(customer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer = customerService.saveCustomer(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @PutMapping("/{id}")
    public CustomerResponse updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerService.validateAndGetCustomer(id);
        customerMapper.updateCustomerFromRequest(updateCustomerRequest, customer);
        customer = customerService.saveCustomer(customer);
        return customerMapper.toCustomerResponse(customer);
    }

    @DeleteMapping("/{id}")
    public CustomerResponse deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomer(id);
        customerService.deleteCustomer(customer);
        return customerMapper.toCustomerResponse(customer);
    }
}
