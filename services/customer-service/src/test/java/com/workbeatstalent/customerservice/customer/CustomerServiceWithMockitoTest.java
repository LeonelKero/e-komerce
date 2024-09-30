package com.workbeatstalent.customerservice.customer;

import com.workbeatstalent.customerservice.customer.dto.CustomerRequest;
import com.workbeatstalent.customerservice.customer.dto.CustomerResponse;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerEmailAlreadyUsedException;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerNotFoundException;
import com.workbeatstalent.customerservice.customer.util.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(value = {MockitoExtension.class})
class CustomerServiceWithMockitoTest {

    @Mock
    private CustomerRepository repository;

    @Captor
    private ArgumentCaptor<Customer> argumentCaptor;

    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new CustomerService(repository, new CustomerMapper());
    }

    @Test
    void shouldCreateNewCustomerWhenEmailIsAvailable() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        final var response = new Customer(
                "630-476-384",
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        Mockito.when(repository.findByEmail(request.email())).thenReturn(Optional.empty());
        Mockito.when(repository.save(any())).thenReturn(response);
        // WHEN
        underTest.create(request);
        // THEN
        verify(repository, times(1)).save(any(Customer.class));
    }

    @Test
    void shouldThrowAnExceptionWhenTryingToCreateNewCustomerWithEmailAlreadyTaken() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        final var response = new Customer(
                "630-476-384",
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        Mockito.when(repository.findByEmail(request.email())).thenReturn(Optional.of(response));
        // WHEN // THEN
        verify(repository, times(0)).save(any());
        assertThatThrownBy(() -> underTest.create(request))
                .isInstanceOf(CustomerEmailAlreadyUsedException.class)
                .hasMessage("Email '%s' already taken", request.email());
    }

    @Test
    void shouldUpdateExistingCustomerOnlyIfAtLeastOnePropertyHadChanged() {
        // GIVEN
        // Here only the name has changed
        final var customerId = "630-476-384";
        final var request = new CustomerRequest(
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        final var response = new Customer(
                customerId,
                "leonel nero",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        when(repository.findById(customerId)).thenReturn(Optional.of(response));
        // WHEN
        underTest.update(customerId, request);
        // THEN
        verify(repository).save(argumentCaptor.capture());
        final var capturedCustomer = argumentCaptor.getValue();

        assertThat(capturedCustomer.getFirstname()).isEqualTo(request.firstname());
        assertThat(capturedCustomer.getLastname()).isEqualTo(request.lastname());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());
        assertThat(capturedCustomer.getAddress().getCity()).isEqualTo(request.address().getCity());
        assertThat(capturedCustomer.getAddress().getTown()).isEqualTo(request.address().getTown());
        assertThat(capturedCustomer.getAddress().getStreet()).isEqualTo(request.address().getStreet());
    }

    @Test
    @Disabled
    void shouldNotUpdateExistingCustomerIfNoneOfThePropertiesHadChanged() {
        // GIVEN
        // Here none of the updatable properties had changed
        final var customerId = "630-476-384";
        final var request = new CustomerRequest(
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        final var response = new Customer(
                customerId,
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        when(repository.findById(customerId)).thenReturn(Optional.of(response));
        // WHEN
        underTest.update(customerId, request);
        // THEN
        // Todo: Must be fixed
        verify(repository, times(0)).save(any(Customer.class));
    }

    @Test
    void shouldThrowAnExceptionWhenTryingToUpdateNotExistingCustomer() {
        // GIVEN
        final var fakeId = "fake Id";
        final var request = new CustomerRequest(
                "mysterious",
                "name",
                "malicious.name@danger.com",
                new Address("Usa", "La", "45 vt"));
        when(repository.findById(any(String.class))).thenReturn(Optional.empty());
        // WHEN // THEN
        assertThatThrownBy(() -> underTest.update(fakeId, request))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer with ID %s not found", fakeId);
        verify(repository, times(0)).save(any());
    }

    @Test
    void shouldGetExistingCustomerByItsIdAsCustomerResponseType() {
        // GIVEN
        final var customerId = "630-476-384";
        final var response = new Customer(
                customerId,
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        when(repository.findById(customerId)).thenReturn(Optional.of(response));
        // WHEN
        final var result = underTest.getById(customerId);
        // THEN
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(CustomerResponse.class);
    }

    @Test
    void shouldAnThrowAnExceptionWhenTryingToGetNotExistingCustomer() {
        // GIVEN
        when(repository.findById(any(String.class))).thenReturn(Optional.empty());
        // WHEN // THEN
        final var fakeId = "fake ID";
        assertThatThrownBy(() -> underTest.getById(fakeId))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer with ID %s not found", fakeId);
    }

    @Test
    void shouldGetAllCustomers() {
        // GIVEN // WHEN
        final var customers = underTest.getAll();
        // THEN
        verify(repository).findAll();
        assertThat(customers).hasSize(0);
    }

    @Test
    void shouldDeleteCustomerByIdWhenCustomerExists() {
        // GIVEN
        final var customerId = "630-476-384";
        final var response = new Customer(
                customerId,
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        when(repository.findById(customerId)).thenReturn(Optional.of(response));
        // WHEN
        underTest.deleteById(customerId);
        // THEN
        verify(repository, times(1)).deleteById(customerId);
    }

    @Test
    void shouldAnExceptionWhenTryingToDeleteNotExistingCustomer() {
        // GIVEN
        final var fakeId = "not even an Id";
        when(repository.findById(fakeId)).thenReturn(Optional.empty());
        // WHEN // THEN
        verify(repository, times(0)).deleteById(any());
        assertThatThrownBy(() -> underTest.deleteById(fakeId))
                .hasMessage("Customer with ID %s not found", fakeId)
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void shouldCheckIfCustomerExistsBasedOnItsIdThenTrue() {
        // GIVEN
        final var customerId = "630-476-384";
        final var response = new Customer(
                customerId,
                "leonel",
                "ka",
                "workbeattalent@gmail.com",
                new Address("Baf", "Bgo", "volt"));
        when(repository.findById(customerId)).thenReturn(Optional.of(response));
        // WHEN
        final var result = underTest.existsById(customerId);
        // THEN
        verify(repository).findById(customerId);
        assertThat(result).isTrue();
    }

    @Test
    void shouldCheckIfCustomerExistsBasedOnItsIdThenFalse() {
        // GIVEN
        final var customerId = "fake id value";
        when(repository.findById(any(String.class))).thenReturn(Optional.empty());
        // WHEN
        final var result = underTest.existsById(customerId);
        // THEN
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueIfAnyCustomerExistsWithASpecificFirstname() {
        // GIVEN
        final var firstname = "leonel";
        when(repository.existsByFirstname(firstname)).thenReturn(true);
        // WHEN
        final var result = underTest.existsByFirstname(firstname);
        // THEN
        assertThat(result).isTrue();
    }


    @Test
    void shouldReturnFalseIfAnyCustomerDoNotExistsWithASpecificFirstname() {
        when(repository.existsByFirstname(any(String.class))).thenReturn(false);
        final var result = underTest.existsByFirstname("random firstname");
        assertThat(result).isFalse();
    }

    @Test
    void shouldReturnTrueIfAnyCustomerExistsWithASpecificLastname() {
        // GIVEN
        final var lastname = "leonel";
        when(repository.existsByLastname(lastname)).thenReturn(true);
        // WHEN
        final var result = underTest.existsByLastname(lastname);
        // THEN
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseIfAnyCustomerDoNotExistsWithASpecificLastname() {
        when(repository.existsByLastname(any(String.class))).thenReturn(false);
        final var result = underTest.existsByLastname("random lastname");
        assertThat(result).isFalse();
    }
}