package com.ivanfranchin.customerapi.mapper;

import com.ivanfranchin.customerapi.model.Customer;
import com.ivanfranchin.customerapi.rest.dto.CreateCustomerRequest;
import com.ivanfranchin.customerapi.rest.dto.CustomerResponse;
import com.ivanfranchin.customerapi.rest.dto.UpdateCustomerRequest;
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
