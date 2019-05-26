package com.mycompany.springbootproxysqlmysql.rest;

import com.mycompany.springbootproxysqlmysql.model.Customer;
import com.mycompany.springbootproxysqlmysql.rest.dto.CreateCustomerDto;
import com.mycompany.springbootproxysqlmysql.rest.dto.CustomerDto;
import com.mycompany.springbootproxysqlmysql.rest.dto.UpdateCustomerDto;
import com.mycompany.springbootproxysqlmysql.service.CustomerService;
import ma.glasnost.orika.MapperFacade;
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

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final MapperFacade mapperFacade;

    public CustomerController(CustomerService customerService, MapperFacade mapperFacade) {
        this.customerService = customerService;
        this.mapperFacade = mapperFacade;
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers()
                .stream()
                .map(customer -> mapperFacade.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomer(id);
        return mapperFacade.map(customer, CustomerDto.class);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CustomerDto createCustomer(@Valid @RequestBody CreateCustomerDto createCustomerDto) {
        Customer customer = mapperFacade.map(createCustomerDto, Customer.class);
        customer = customerService.saveCustomer(customer);
        return mapperFacade.map(customer, CustomerDto.class);
    }

    @PutMapping("/{id}")
    public CustomerDto updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerDto updateCustomerDto) {
        Customer customer = customerService.validateAndGetCustomer(id);
        mapperFacade.map(updateCustomerDto, customer);
        customer = customerService.saveCustomer(customer);
        return mapperFacade.map(customer, CustomerDto.class);
    }

    @DeleteMapping("/{id}")
    public CustomerDto deleteCustomer(@PathVariable Long id) {
        Customer customer = customerService.validateAndGetCustomer(id);
        customerService.deleteCustomer(customer);
        return mapperFacade.map(customer, CustomerDto.class);
    }
}
