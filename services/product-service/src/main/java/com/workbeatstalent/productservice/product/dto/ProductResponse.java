package com.workbeatstalent.productservice.product.dto;

import com.workbeatstalent.productservice.category.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Integer inInventory,
        BigDecimal price,
        Category category
) {
}
