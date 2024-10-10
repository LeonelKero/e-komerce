package com.workbeattalent.paymentservice.payment.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated

@Validated
data class Customer(
    val id: String,
    @NotNull(message = "Firstname is required")
    val firstname: String,
    @NotNull(message = "Lastname is required")
    val lastname: String,
    @NotNull(message = "Email is require")
    @Email(message = "Please provide a valid email")
    val email: String,
)
