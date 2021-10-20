package com.mycompany.customerapi.rest.dto;

import lombok.Value;

@Value
public class CustomerResponse {

    Long id;
    String firstName;
    String lastName;
}
