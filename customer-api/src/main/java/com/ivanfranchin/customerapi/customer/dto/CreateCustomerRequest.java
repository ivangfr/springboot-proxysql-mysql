package com.ivanfranchin.customerapi.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCustomerRequest(@NotBlank String firstName, @NotBlank String lastName) {
}
