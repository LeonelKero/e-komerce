package com.workbeatstalent.customerservice.customer.util;

import com.workbeatstalent.customerservice.customer.Address;
import com.workbeatstalent.customerservice.customer.Customer;
import com.workbeatstalent.customerservice.customer.dto.CustomerRequest;
import com.workbeatstalent.customerservice.customer.dto.CustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerMapperTest {

    private CustomerMapper underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new CustomerMapper();
    }

    @Test
    void givenCustomerRequestMapItToCustomer() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonal ka",
                "nero",
                "workbeattalen@gmail.com",
                new Address("Yde", "Btos", "Pls"));
        // WHEN
        final var customer = underTest.toCustomer(request);
        // THEN
        assertThat(customer).isInstanceOf(Customer.class);
    }

    @Test
    void givenNullCustomerRequestMapItToCustomer_thenReturnNull() {
        assertThat(underTest.toCustomer(null)).isNull();
    }

    @Test
    void givenCustomerMapItToCustomerResponse() {
        // GIVEN
        final var customer = new Customer(
                "7462-57264-24-245-52000",
                "leonal ka",
                "nero",
                "workbeattalen@gmail.com",
                new Address("Yde", "Btos", "Pls"));
        // WHEN
        final var response = underTest.toResponse(customer);
        // THEN
        assertThat(response).isInstanceOf(CustomerResponse.class);
    }


    @Test
    void givenNullCustomerMapItToCustomerResponse_thenReturnNull() {
        assertThat(underTest.toResponse(null)).isNull();
    }
}