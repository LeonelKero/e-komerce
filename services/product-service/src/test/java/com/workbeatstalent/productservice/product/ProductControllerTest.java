package com.workbeatstalent.productservice.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workbeatstalent.productservice.category.Category;
import com.workbeatstalent.productservice.product.dto.ProductPurchaseRequest;
import com.workbeatstalent.productservice.product.dto.ProductPurchaseResponse;
import com.workbeatstalent.productservice.product.dto.ProductRequest;
import com.workbeatstalent.productservice.product.dto.ProductResponse;
import com.workbeatstalent.productservice.product.exceptions.ProductNotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {ProductController.class})
class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final static String API_PATH = "/api/v1/products";

    @Test
    void whenProductIsValidSaved_thenOK() throws Exception {
        // GIVEN
        final var request = new ProductRequest(
                "Macbook Pro 16'",
                "Performant computer for professionals",
                2,
                BigDecimal.valueOf(1200),
                1
        );
        Mockito.when(productService.save(any())).thenReturn(any(Integer.class));
        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenProductIsInValidThrowAnException_thenBAD_REQUEST() throws Exception {
        // GIVEN
        final var request = new ProductRequest(
                null,
                "Performant computer for professionals",
                2,
                BigDecimal.valueOf(1200),
                1
        );
        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenProductItemsAreAvailableMakePurchase_thenOK() throws Exception {
        // GIVEN
        final var prc1 = new ProductPurchaseRequest(2, 3);
        final var prc2 = new ProductPurchaseRequest(1, 3);
        final var purchases = List.of(prc1, prc2);
        final var responses = List.of(
                new ProductPurchaseResponse(1, "macbook pro 16", "professional computer", BigDecimal.valueOf(2500), 3),
                new ProductPurchaseResponse(1, "iphone 16 pro", ".great phone", BigDecimal.valueOf(1500), 2)
        );
        Mockito.when(productService.purchases(anyList())).thenReturn(responses);
        // WHEN  // THEN
        mockMvc.perform(MockMvcRequestBuilders.post(API_PATH + "/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchases)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", Matchers.is(responses.size())));
    }

    @Test
    void whenProductExistsGetItById_thenOK() throws Exception {
        // GIVEN
        final var response = new ProductResponse(
                1,
                "Macbook pro M3 Max",
                "Performant computer for professionals",
                2,
                BigDecimal.valueOf(1200),
                new Category(3, "Tech", "Related technology equipment", null)
        );
        Mockito.when(productService.getById(any(Integer.class)))
                .thenReturn(response);
        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/1"))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(response))
                );
    }


    @Test
    void whenProductDoesNotExistsGetItByIdThrowAnException_thenNOT_FOUND() throws Exception {
        // GIVEN
        Mockito.when(productService.getById(anyInt()))
                .thenThrow(ProductNotFoundException.class);
        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH + "/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGettingProducts_thenOK() throws Exception {
        // GIVEN
        final var pr1 = new ProductResponse(
                1,
                "Sony remote controller",
                "Robust Sony remote controller for your TV",
                50,
                BigDecimal.valueOf(200),
                new Category(3, "Tech", "Related technology equipment", null)
        );
        final var pr2 = new ProductResponse(
                1,
                "Galaxy S10+",
                "Samsung Galaxy S10+ released in 2019",
                80,
                BigDecimal.valueOf(900),
                new Category(3, "Mobile", "Mobile equipment", null)
        );
        final var responseList = List.of(pr1, pr2);
        Mockito.when(productService.getAll()).thenReturn(responseList);
        // WHEN // THEN
        mockMvc.perform(MockMvcRequestBuilders.get(API_PATH))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(responseList.size())));
    }
}