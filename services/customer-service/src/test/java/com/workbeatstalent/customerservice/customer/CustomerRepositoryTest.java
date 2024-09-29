package com.workbeatstalent.customerservice.customer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerRepositoryTest extends AbstractBaseTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void shouldFindCustomerByEmail() {
        // GIVEN
        final var customer = Customer.builder()
                .firstname("leonel ka")
                .lastname("nero")
                .email("workbeattalent@gmail.com")
                .address(new Address("Bafoussam", "Banengo", "volt"))
                .build();
        final var savedCustomer = underTest.save(customer);
        // WHEN
        final var optionalCustomer = underTest.findByEmail(customer.getEmail());
        // THEN
        assertThat(optionalCustomer.isPresent()).isTrue();
        assertThat(optionalCustomer.get().getEmail()).isEqualTo(savedCustomer.getEmail());
    }

    @Test
    void shouldSaidIfCustomerExistsByFirstname() {
        // GIVEN
        final var firstname = "leonel ka";
        final var customer = Customer.builder()
                .firstname(firstname)
                .lastname("nero")
                .email("workbeattalent@gmail.com")
                .address(new Address("Bafoussam", "Banengo", "volt"))
                .build();
        underTest.save(customer);
        // WHEN
        final var isCustomerPresent = underTest.existsByFirstname(firstname);
        // THEN
        assertThat(isCustomerPresent).isTrue();
    }

    @Test
    void shouldSaidIfCustomerExistsByLastname() {
        // GIVEN
        final var lastname = "nero";
        final var customer = Customer.builder()
                .firstname("leonel ka")
                .lastname(lastname)
                .email("workbeattalent@gmail.com")
                .address(new Address("Bafoussam", "Banengo", "volt"))
                .build();
        underTest.save(customer);
        // WHEN
        final var isCustomerPresent = underTest.existsByLastname(lastname);
        // THEN
        assertThat(isCustomerPresent).isTrue();
    }
}