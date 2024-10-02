package com.workbeatstalent.productservice.product.util;

import com.workbeatstalent.productservice.category.Category;
import com.workbeatstalent.productservice.product.Product;
import com.workbeatstalent.productservice.product.dto.ProductPurchaseResponse;
import com.workbeatstalent.productservice.product.dto.ProductRequest;
import com.workbeatstalent.productservice.product.dto.ProductResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public Product toProduct(final ProductRequest request, final Integer productId) {
        if (productId != null)
            return Product.builder()
                    .id(productId)
                    .name(request.name())
                    .description(request.description())
                    .availableQuantity(request.quantity())
                    .price(request.price())
                    .category(Category.builder().id(request.categoryId()).build())
                    .build();
        return Product.builder()
                .name(request.name())
                .description(request.description())
                .availableQuantity(request.quantity())
                .price(request.price())
                .category(Category.builder().id(request.categoryId()).build())
                .build();
    }

    public ProductResponse toResponse(final Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory()
        );
    }

    public ProductPurchaseResponse toPurchaseResponse(final Product product, final Integer requestedQuantity) {
        return new ProductPurchaseResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), requestedQuantity);
    }
}
