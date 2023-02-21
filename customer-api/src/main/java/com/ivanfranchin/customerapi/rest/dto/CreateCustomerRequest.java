package com.ivanfranchin.customerapi.rest.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(@NotBlank String firstName, @NotBlank String lastName) {
}
