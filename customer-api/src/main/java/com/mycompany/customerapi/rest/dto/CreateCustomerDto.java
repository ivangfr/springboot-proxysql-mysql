package com.mycompany.customerapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateCustomerDto {

    @NotBlank
    @ApiModelProperty(example = "Ivan")
    private String firstName;

    @NotBlank
    @ApiModelProperty(position = 1, example = "Franchin")
    private String lastName;

}
