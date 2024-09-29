package com.workbeatstalent.customerservice.customer;

import com.workbeatstalent.customerservice.customer.dto.CustomerRequest;
import com.workbeatstalent.customerservice.customer.dto.CustomerResponse;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerEmailAlreadyUsedException;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerNotFoundException;
import com.workbeatstalent.customerservice.customer.util.CustomerMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository repository, CustomerMapper customerMapper) {
        this.repository = repository;
        this.customerMapper = customerMapper;
    }

    public String create(final CustomerRequest customer) {
        if (this.repository.findByEmail(customer.email()).isPresent()) {
            // Meaning this email is already used
            throw new CustomerEmailAlreadyUsedException(String.format("Email '%s' already taken", customer.email()));
        }
        final var savedCustomer = this.repository.save(customerMapper.toCustomer(customer));
        return savedCustomer.getId();
    }

    public void update(final String id, final CustomerRequest customer) {
        final var existingCustomer = this.repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with ID %s not found", id)));

        mergeCustomer(customer, existingCustomer);
        this.repository.save(existingCustomer);
    }

    public CustomerResponse getById(final String customerId) {
        return this.repository.findById(customerId)
                .map(customerMapper::toResponse)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("Customer with ID %s not found", customerId)));
    }

    public List<CustomerResponse> getAll() {
        return this.repository.findAll()
                .stream()
                .map(customerMapper::toResponse)
                .toList();
    }

    public void deleteById(final String customerId) {
        final var customer = this.getById(customerId);
        this.repository.deleteById(customer.id());
    }

    public Boolean existsById(final String customerId) {
        return this.repository.findById(customerId).isPresent();
    }

    public Boolean existsByFirstname(final String firstname) {
        return this.repository.existsByFirstname(firstname);
    }

    public Boolean existsByLastname(final String lastname) {
        return this.repository.existsByLastname(lastname);
    }

    private void mergeCustomer(final CustomerRequest request, final Customer existingCustomer) {
        if (StringUtils.isNotBlank(request.firstname().trim()) && !request.firstname().equals(existingCustomer.getFirstname())) {
            existingCustomer.setFirstname(request.firstname().trim());
        }
        if (StringUtils.isNotBlank(request.lastname().trim()) && !request.firstname().equals(existingCustomer.getLastname())) {
            existingCustomer.setLastname(request.lastname().trim());
        }
        if (StringUtils.isNotBlank(request.email().trim()) && !request.firstname().equals(existingCustomer.getEmail())) {
            // Meaning customer has changed his/her email
            // So, check if that email is not already in use
            final var customer = this.repository.findByEmail(request.email());
            if (customer.isEmpty()) {
                // so, nobody with that email exists
                existingCustomer.setEmail(request.email().trim());
            }
            // Otherwise, keep the existing email
        }
        if (request.address() != null) {
            existingCustomer.setAddress(request.address());
        }

    }
}
