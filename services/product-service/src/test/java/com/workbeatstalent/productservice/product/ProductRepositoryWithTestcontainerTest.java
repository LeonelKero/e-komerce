package com.workbeatstalent.productservice.product;

import com.workbeatstalent.productservice.AbstractContainerTest;
import com.workbeatstalent.productservice.category.Category;
import com.workbeatstalent.productservice.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryWithTestcontainerTest extends AbstractContainerTest {

    @Autowired
    private ProductRepository underTest;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void shouldFindProductByName() {
        // GIVEN
        final var newCategory = Category.builder()
                .name("Machines")
                .description("All electronic devices")
                .build();
        final var savedCategory = categoryRepository.save(newCategory);
        System.out.println(savedCategory);
        final var productName = "Macbook Pro 16";
        final var product = Product.builder()
                .name(productName)
                .description("Good computer for professionals")
                .availableQuantity(10)
                .price(BigDecimal.valueOf(900_000))
                .category(savedCategory)
                .build();
        underTest.save(product);
        // WHEN
        final var optionalProduct = underTest.findByName(productName);
        // THEN
        assertThat(optionalProduct).isPresent();
    }
}