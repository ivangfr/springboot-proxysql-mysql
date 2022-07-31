package com.ivanfranchin.customerapi.rest.dto;

import javax.validation.constraints.NotBlank;

public record CreateCustomerRequest(@NotBlank String firstName, @NotBlank String lastName) {
}
