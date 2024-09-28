package com.workbeatstalent.customerservice.customer.dto;

import com.workbeatstalent.customerservice.customer.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
        @NotNull(message = "Customer firstname can't be null")
        String firstname,
        @NotNull(message = "Customer lastname can't be null")
        String lastname,
        @NotNull(message = "Customer email can't be null")
        @Email(message = "An email is required")
        String email,
        Address address
) {
}
