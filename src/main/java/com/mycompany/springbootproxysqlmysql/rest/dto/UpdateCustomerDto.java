package com.mycompany.springbootproxysqlmysql.rest.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateCustomerDto {

    @ApiModelProperty(example = "Ivan2")
    private String firstName;

    @ApiModelProperty(example = "Franchin2", position = 2)
    private String lastName;

}
