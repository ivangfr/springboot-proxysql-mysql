package com.mycompany.customerapi.mapper;

import com.mycompany.customerapi.model.Customer;
import com.mycompany.customerapi.rest.dto.CreateCustomerRequest;
import com.mycompany.customerapi.rest.dto.CustomerResponse;
import com.mycompany.customerapi.rest.dto.UpdateCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerResponse toCustomerResponse(Customer customer);

    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    void updateCustomerFromResponse(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);
}
