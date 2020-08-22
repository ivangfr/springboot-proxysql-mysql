package com.mycompany.customerapi.mapper;

import com.mycompany.customerapi.model.Customer;
import com.mycompany.customerapi.rest.dto.CreateCustomerRequest;
import com.mycompany.customerapi.rest.dto.CustomerDto;
import com.mycompany.customerapi.rest.dto.UpdateCustomerRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerDto toCustomerDto(Customer customer);

    Customer toCustomer(CreateCustomerRequest createCustomerRequest);

    void updateCustomerFromDto(UpdateCustomerRequest updateCustomerRequest, @MappingTarget Customer customer);

}
