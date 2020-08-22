package com.mycompany.customerapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateCustomerRequest {

    @Schema(example = "Ivan2")
    private String firstName;

    @Schema(example = "Franchin2")
    private String lastName;

}
