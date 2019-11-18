package com.mycompany.customerapi.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateCustomerDto {

    @ApiModelProperty(example = "Ivan2")
    private String firstName;

    @ApiModelProperty(position = 1, example = "Franchin2")
    private String lastName;

}
