package com.mycompany.customerapi.rest;

import com.mycompany.customerapi.mapper.CustomerMapper;
import com.mycompany.customerapi.model.Customer;
import com.mycompany.customerapi.rest.dto.CreateCustomerRequest;
import com.mycompany.customerapi.rest.dto.CustomerDto;
import com.mycompany.customerapi.rest.dto.UpdateCustomerRequest;
import com.mycompany.customerapi.service.CustomerService;
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
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customerMapper::toCustomerDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomer(id);
        return customerMapper.toCustomerDto(customer);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerMapper.toCustomer(createCustomerRequest);
        customer = customerService.saveCustomer(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerService.validateAndGetCustomer(id);
        customerMapper.updateCustomerFromDto(updateCustomerRequest, customer);
        customer = customerService.saveCustomer(customer);
        return customerMapper.toCustomerDto(customer);
    }

    @DeleteMapping("/{id}")
    public CustomerDto deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomer(id);
        customerService.deleteCustomer(customer);
        return customerMapper.toCustomerDto(customer);
    }
}
