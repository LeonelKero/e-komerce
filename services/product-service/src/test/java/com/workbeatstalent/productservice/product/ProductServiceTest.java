package com.workbeatstalent.productservice.product;

import com.workbeatstalent.productservice.category.Category;
import com.workbeatstalent.productservice.product.dto.ProductPurchaseRequest;
import com.workbeatstalent.productservice.product.dto.ProductRequest;
import com.workbeatstalent.productservice.product.dto.ProductResponse;
import com.workbeatstalent.productservice.product.exceptions.ProductNotFoundException;
import com.workbeatstalent.productservice.product.exceptions.ProductPurchaseException;
import com.workbeatstalent.productservice.product.util.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(value = {MockitoExtension.class})
class ProductServiceTest {

    private ProductService underTest;

    @Mock
    private ProductRepository repository;

    @Captor
    private ArgumentCaptor<Product> argumentCaptor;

    @BeforeEach
    void setUp() {
        underTest = new ProductService(repository, new ProductMapper());
    }

    @Test
    void shouldSaveNewProduct() {
        // GIVEN
        final var request = new ProductRequest(
                "Sony remote controller",
                "Robust Sony remote controller for your TV",
                50,
                BigDecimal.valueOf(200),
                1);
        final var product = new Product(
                1,
                "Sony remote controller",
                "Robust Sony remote controller for your TV",
                50,
                BigDecimal.valueOf(200),
                new Category(3, "Tech", "Related technology equipment", null)
        );
        when(repository.save(any(Product.class))).thenReturn(product);
        // WHEN
        final var result = underTest.save(request);
        // THEN
        verify(repository).save(argumentCaptor.capture());
        final var capturedProduct = argumentCaptor.getValue();

        verify(repository, times(1)).save(any(Product.class));

        assertThat(capturedProduct.getName()).isEqualTo(request.name());
        assertThat(capturedProduct.getDescription()).isEqualTo(request.description());
        assertThat(capturedProduct.getAvailableQuantity()).isEqualTo(request.quantity());
        assertThat(capturedProduct.getPrice()).isEqualTo(request.price());
        assertThat(capturedProduct.getCategory()).isNotNull();
    }

    @Test
    void shouldPurchasesAnyAvailableProduct() {
        // GIVEN
        final var p1 = new ProductPurchaseRequest(1, 3);
        final var p2 = new ProductPurchaseRequest(2, 1);
        final var prs = List.of(p1, p2);

        final var r1 = new Product(
                1,
                "Sony remote controller",
                "Robust Sony remote controller for your TV",
                50,
                BigDecimal.valueOf(200),
                new Category(3, "Tech", "Related technology equipment", null)
        );
        final var r2 = new Product(
                2,
                "PS5 Pro",
                "Playstation Gen 5",
                15,
                BigDecimal.valueOf(200),
                new Category(2, "Console", "All console and games", null));
        final var responseList = List.of(r1, r2);

        when(repository.findAllByIdInOrderById(any())).thenReturn(responseList);
        // WHEN
        final var result = underTest.purchases(prs);
        // THEN
        verify(repository, times(2)).save(any(Product.class));
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void shouldThrowAnExceptionWhenPurchaseListHoldsNotExistingProduct() {
        // GIVEN
        final var p1 = new ProductPurchaseRequest(1, 3);
        final var p2 = new ProductPurchaseRequest(2, 1);
        final var prs = List.of(p1, p2);

        final var r1 = new Product(
                1,
                "Sony remote controller",
                "Robust Sony remote controller for your TV",
                50,
                BigDecimal.valueOf(200),
                new Category(3, "Tech", "Related technology equipment", null)
        );
        final var responseList = List.of(r1);
        when(repository.findAllByIdInOrderById(any())).thenReturn(responseList);
        // WHEN // THEN
        assertThatThrownBy(() -> underTest.purchases(prs))
                .isInstanceOf(ProductPurchaseException.class)
                .hasMessage("One or more products does not exists");
        verify(repository, times(0)).save(any());
    }

    @Test
    void shouldThrownAnExceptionWhenTryingToPurchaseProductWithAvailableQuantityLessThanRequested() {
        // GIVEN
        final var p1 = new ProductPurchaseRequest(1, 3);
        final var p2 = new ProductPurchaseRequest(2, 20);
        final var prs = List.of(p1, p2);

        final var r1 = new Product(
                1,
                "Sony remote controller",
                "Robust Sony remote controller for your TV",
                50,
                BigDecimal.valueOf(200),
                new Category(3, "Tech", "Related technology equipment", null)
        );
        final var r2 = new Product(
                2,
                "PS5 Pro",
                "Playstation Gen 5",
                15,
                BigDecimal.valueOf(200),
                new Category(2, "Console", "All console and games", null));
        final var responseList = List.of(r1, r2);

        when(repository.findAllByIdInOrderById(any())).thenReturn(responseList);
        // WHEN // THEN
        assertThatThrownBy(() -> underTest.purchases(prs))
                .isInstanceOf(ProductPurchaseException.class)
                .hasMessage("There not enough quantity of %s, available quantity is %s unit(s)",
                        r2.getName(),
                        r2.getAvailableQuantity());
        verify(repository, times(1)).save(any());
    }

    @Test
    void shouldGetProductById() {
        // GIVEN
        final var productId = 1;
        final var product = new Product(
                productId,
                "Wireless mice",
                "Fast and reliable cordeless mice for pros",
                7,
                BigDecimal.valueOf(10.0),
                new Category(3, "Tech", "Related technology equipment", null)
        );
        when(repository.findById(any(Integer.class))).thenReturn(Optional.of(product));
        // WHEN
        final var response = underTest.getById(productId);
        // THEN
        verify(repository, times(1)).findById(any(Integer.class));
        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(ProductResponse.class);
    }

    @Test
    void shouldThrowAnExceptionWhenTryingGetNotExistingProduct() {
        // GIVEN
        final var fakeId = -999;
        // WHEN
        when(repository.findById(any(Integer.class))).thenReturn(Optional.empty());
        // THEN
        verify(repository, times(0)).findById(any(Integer.class));
        assertThatThrownBy(() -> underTest.getById(fakeId))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Product with ID '%s' not found", fakeId);
    }

    @Test
    void shouldGetAllProducts() {
        // GIVEN // WHEN
        underTest.getAll();
        // THEN
        verify(repository, times(1)).findAll();
    }
}