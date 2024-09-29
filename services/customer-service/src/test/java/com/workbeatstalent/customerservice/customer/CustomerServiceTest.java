package com.workbeatstalent.customerservice.customer;

import com.workbeatstalent.customerservice.customer.dto.CustomerRequest;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerEmailAlreadyUsedException;
import com.workbeatstalent.customerservice.customer.exceptions.CustomerNotFoundException;
import com.workbeatstalent.customerservice.customer.util.CustomerMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class CustomerServiceTest extends AbstractBaseTest {

    @Autowired
    private CustomerService underTest;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerMapper mapper;

    @BeforeEach
    void setUp() {
        this.underTest = new CustomerService(customerRepository, mapper);
        customerRepository.deleteAll();
    }

    @Test
    void givenAValidCustomerCreateAndWhenEmailIsNotYetTaken_thenReturnId() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel ka",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        // WHEN
        final var customerId = underTest.create(request);
        // THEN
        assertThat(customerId)
                .isNotNull()
                .isInstanceOf(String.class);
    }

    @Test
    void givenAValidCustomerCreatedWithAlreadyUsedEmail_thenThrownAnException() {
        // GIVEN
        final var email = "workbeattalent@gmailcom";
        final var request1 = new CustomerRequest(
                "leonel ka",
                "nero",
                email,
                new Address("Baf", "Banengo", "volt"));
        underTest.create(request1);

        final var request2 = new CustomerRequest(
                "leona",
                "til",
                email,
                new Address("Yde", "Bata", "Carr"));
        // WHEN // THEN
        assertThatThrownBy(() -> underTest.create(request2))
                .isInstanceOf(CustomerEmailAlreadyUsedException.class)
                .hasMessage("Email '%s' already taken", email);
    }

    @Test
    void givenAValidCustomerRequestForUpdateOnAlreadyExistingCustomer_thenOK() {
        // GIVEN
        final var newCustomer = new CustomerRequest(
                "leonel ka",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        final var customerId = underTest.create(newCustomer);
        // WHEN
        final var requestForUpdate = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        underTest.update(customerId, requestForUpdate);
        // THEN
        final var customer = underTest.getById(customerId);
        assertThat(customer.firstname()).isEqualTo(requestForUpdate.firstname());
        assertThat(customer.lastname()).isEqualTo(requestForUpdate.lastname());
        assertThat(customer.email()).isEqualTo(requestForUpdate.email());
        assertThat(customer.address().getCity()).isEqualTo(requestForUpdate.address().getCity());
        assertThat(customer.address().getTown()).isEqualTo(requestForUpdate.address().getTown());
        assertThat(customer.address().getStreet()).isEqualTo(requestForUpdate.address().getStreet());
    }

    @Test
    void givenAValidCustomerRequestForUpdateOnExistingCustomerWithAllNewDetailsAndEmailNotAlreadyTaken_thenOK() {
        // GIVEN
        final var newCustomer = new CustomerRequest(
                "leonel ka",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        final var customerId = underTest.create(newCustomer);
        // WHEN
        final var requestForUpdate = new CustomerRequest(
                "leonel kanm",
                "nero",
                "new.workbeattalent@gmailcom",
                new Address("Baf new", "Banengo new", "volt new"));
        underTest.update(customerId, requestForUpdate);
        // THEN
        final var customer = underTest.getById(customerId);
        assertThat(customer.firstname()).isEqualTo(requestForUpdate.firstname());
        assertThat(customer.lastname()).isEqualTo(requestForUpdate.lastname());
        assertThat(customer.email()).isEqualTo(requestForUpdate.email());
        assertThat(customer.address().getCity()).isEqualTo(requestForUpdate.address().getCity());
        assertThat(customer.address().getTown()).isEqualTo(requestForUpdate.address().getTown());
        assertThat(customer.address().getStreet()).isEqualTo(requestForUpdate.address().getStreet());
    }

    @Test
    void givenAnUnknownCustomerIdAForRequestForUpdate_thenThrownException() {
        // GIVEN
        final var fakeCustomerId = "this is not even a customer id";
        // WHEN
        final var request = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        // THEN
        assertThatThrownBy(() -> underTest.update(fakeCustomerId, request))
                .isInstanceOf(CustomerNotFoundException.class)
                .hasMessage("Customer with ID %s not found", fakeCustomerId);
    }

    @Test
    void givenIdOfAnExistingCustomerForRetrievingThatCustomer_thenOK() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        final var customerId = underTest.create(request);
        // WHEN
        final var customer = underTest.getById(customerId);
        // THEN
        assertThat(customer.id()).isNotNull();
        assertThat(customer.firstname()).isEqualTo(request.firstname());
        assertThat(customer.lastname()).isEqualTo(request.lastname());
        assertThat(customer.email()).isEqualTo(request.email());
        assertThat(customer.address().getCity()).isEqualTo(request.address().getCity());
        assertThat(customer.address().getTown()).isEqualTo(request.address().getTown());
        assertThat(customer.address().getStreet()).isEqualTo(request.address().getStreet());
    }

    @Test
    void givenAFakeCustomerIdForRetrievingCustomer_thenThrownException() {
        // GIVEN
        final var fakeCustomerId = "not even an id";
        // WHEN // THEN
        assertThatThrownBy(() -> underTest.getById(fakeCustomerId))
                .hasMessage("Customer with ID %s not found", fakeCustomerId)
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void gettingAllCustomers_thenOK() {
        // GIVEN
        final var request1 = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        final var request2 = new CustomerRequest(
                "leona",
                "neb",
                "leona.workbeattalent@gmailcom",
                new Address("Yde", "Bastos", "Pl"));
        underTest.create(request1);
        underTest.create(request2);
        // WHEN
        final var customers = underTest.getAll();
        // THEN
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void givenAnIdOfExistingCustomerForDeletion_thenOK() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        final var customerId = underTest.create(request);
        // WHEN
        underTest.deleteById(customerId);
        // THEN
        assertThatThrownBy(() -> underTest.getById(customerId))
                .hasMessage("Customer with ID %s not found", customerId)
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void givenAnIdForNotExistingCustomerForDeletion_thrownException() {
        // GIVEN
        final var fakeCustomerId = "not even an id";
        // WHEN // THEN
        assertThatThrownBy(() -> underTest.deleteById(fakeCustomerId))
                .hasMessage("Customer with ID %s not found", fakeCustomerId)
                .isInstanceOf(CustomerNotFoundException.class);
    }

    @Test
    void givenAnIdOfExistingCustomerForCheckExistence_thenTrue() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        final var customerId = underTest.create(request);
        // WHEN // THEN
        assertThat(underTest.existsById(customerId)).isTrue();
    }

    @Test
    void givenAFakeIdForCheckingCustomerExistence_thenFalse() {
        // GIVEN
        final var fakeCustomerId = "not even an id";
        // WHEN // THEN
        assertThat(underTest.existsById(fakeCustomerId)).isFalse();
    }

    @Test
    void givenCustomerFirstnameForCheckingExistence_thenTrue() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        underTest.create(request);
        // WHEN // THEN
        assertThat(underTest.existsByFirstname(request.firstname())).isTrue();
    }

    @Test
    void givenCustomerLastnameForCheckingExistence_thenTrue() {
        // GIVEN
        final var request = new CustomerRequest(
                "leonel kanm",
                "nero",
                "workbeattalent@gmailcom",
                new Address("Baf", "Banengo", "volt"));
        underTest.create(request);
        // WHEN // THEN
        assertThat(underTest.existsByLastname(request.lastname())).isTrue();
    }
}