package com.workbeatstalent.customerservice.customer;

import com.workbeatstalent.customerservice.customer.dto.CustomerRequest;
import com.workbeatstalent.customerservice.customer.dto.CustomerResponse;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerEmailAlreadyUsedException;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/customers"})
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> allCustomers() {
        return ResponseEntity.ok(this.customerService.getAll());
    }

    public ResponseEntity<?> getById(final @PathVariable String id) {
        try {
            final var result = this.customerService.getById(id);
            return ResponseEntity.ok(result);
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> addCustomer(final @Valid @RequestBody CustomerRequest request) {
        try {
            final var response = this.customerService.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (CustomerEmailAlreadyUsedException e) {
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

    @PutMapping(path = {"/{id}"})
    public ResponseEntity<?> updateCustomer(final @PathVariable String id, final @Valid @RequestBody CustomerRequest request) {
        try {
            this.customerService.update(id, request);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> removeCustomer(final @PathVariable String id) {
        try {
            this.customerService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (CustomerNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
        }
    }

    @GetMapping(path = {"/exist/{id}"})
    public ResponseEntity<Boolean> customerExistsById(final @PathVariable String id) {
        return ResponseEntity.ok(this.customerService.existsById(id));
    }
}
