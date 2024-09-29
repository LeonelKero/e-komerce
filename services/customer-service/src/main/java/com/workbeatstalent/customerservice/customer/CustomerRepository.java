package com.workbeatstalent.customerservice.customer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmail(final String email);

    Boolean existsByFirstname(final String firstname);

    Boolean existsByLastname(final String lastname);
}
