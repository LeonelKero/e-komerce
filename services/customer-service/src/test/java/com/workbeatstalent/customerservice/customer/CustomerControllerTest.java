package com.workbeatstalent.customerservice.customer;

import com.workbeatstalent.customerservice.customer.dto.CustomerResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(value = {CustomerController.class})
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    private final static String API_PATH = "/api/v1/customers";

    @Test
    void shouldGetAllCustomers() throws Exception {
        // GIVEN
        final var c1 = new CustomerResponse("23948-24", "leonel", "kanm", "workbeattalent@gmail.com", new Address("Baf", "Bgo", "Volt"));
        final var c2 = new CustomerResponse("00948-24", "leona", "zen", "leona.zen@gmail.com", new Address("Yde", "Tsi", "Pol"));
        final var customers = List.of(c1, c2);
        Mockito.when(customerService.getAll()).thenReturn(customers);
        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(
                        "$.size()",
                        Matchers.is(customers.size()))
                );
    }

    @Test
    void shouldGetCustomerById() throws Exception {
        // GIVEN
        final var id = "23948-24";
        final var c1 = new CustomerResponse(id, "leonel", "kanm", "workbeattalent@gmail.com", new Address("Baf", "Bgo", "Volt"));
        Mockito.when(customerService.getById(id)).thenReturn(c1);
        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/" + id))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void addCustomer() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void updateCustomer() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void removeCustomer() {
        // GIVEN

        // WHEN

        // THEN
    }

    @Test
    void customerExistsById() {
        // GIVEN

        // WHEN

        // THEN
    }
}