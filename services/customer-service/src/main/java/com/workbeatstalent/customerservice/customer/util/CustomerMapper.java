package com.workbeatstalent.customerservice.customer.util;

import com.workbeatstalent.customerservice.customer.Customer;
import com.workbeatstalent.customerservice.customer.dto.CustomerRequest;
import com.workbeatstalent.customerservice.customer.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toCustomer(final CustomerRequest request) {
        if (request == null) return null;

        return Customer.builder()
                .firstname(request.firstname().trim())
                .lastname(request.lastname().trim())
                .email(request.email().trim())
                .address(request.address())
                .build();
    }

    public CustomerResponse toResponse(final Customer customer) {
        if (customer == null) return null;
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstname(),
                customer.getLastname(),
                customer.getEmail(),
                customer.getAddress()
        );
    }

}
