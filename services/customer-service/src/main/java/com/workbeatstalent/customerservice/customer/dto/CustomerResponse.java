package com.workbeatstalent.customerservice.customer.dto;

import com.workbeatstalent.customerservice.customer.Address;

public record CustomerResponse(
        String id,
        String firstname,
        String lastname,
        String email,
        Address address) {
}
