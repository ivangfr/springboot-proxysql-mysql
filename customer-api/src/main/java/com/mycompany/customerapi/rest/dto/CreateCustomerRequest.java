package com.mycompany.customerapi.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCustomerRequest {

    @NotBlank
    @Schema(example = "Ivan")
    private String firstName;

    @NotBlank
    @Schema(example = "Franchin")
    private String lastName;

}
