package com.workbeatstalent.productservice.product;

import com.workbeatstalent.productservice.product.dto.ProductPurchaseRequest;
import com.workbeatstalent.productservice.product.dto.ProductPurchaseResponse;
import com.workbeatstalent.productservice.product.dto.ProductRequest;
import com.workbeatstalent.productservice.product.dto.ProductResponse;
import com.workbeatstalent.productservice.product.exceptions.ProductNotFoundException;
import com.workbeatstalent.productservice.product.exceptions.ProductPurchaseException;
import com.workbeatstalent.productservice.product.util.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Integer save(final ProductRequest productRequest) {
        return this.repository
                .save(this.mapper.toProduct(productRequest, null))
                .getId();
    }

    public List<ProductPurchaseResponse> purchases(final List<ProductPurchaseRequest> requestList) {
        final var productIds = requestList.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        final var storedProducts = this.repository.findAllByIdInOrderById(productIds);
        if (storedProducts.size() != productIds.size()) {
            // Means that retrieved list of products does not match the request list length
            throw new ProductPurchaseException("One or more products does not exists");
        }
        // So, all request product for purchase are present
        final var storesRequest = requestList.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        final var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            final var product = storedProducts.get(i);
            final var newQuantity = getQuantity(storesRequest, i, product);
            product.setAvailableQuantity(newQuantity);
            final var saved = this.repository.save(product);
            purchasedProducts.add(this.mapper.toPurchaseResponse(saved, storesRequest.get(i).requestedQuantity()));
        }
        return purchasedProducts;
    }

    private static int getQuantity(List<ProductPurchaseRequest> storesRequest, int i, Product product) {
        final var purchaseRequest = storesRequest.get(i);
        if (product.getAvailableQuantity() < purchaseRequest.requestedQuantity()) {
            // Insufficient quantity in stock
            throw new ProductPurchaseException(String.format(
                    "There not enough quantity of %s, available quantity is %s unit(s)",
                    product.getName(),
                    product.getAvailableQuantity())
            );
        }
        // So, there is enough quantity available in stock
        final var newQuantity = product.getAvailableQuantity() - purchaseRequest.requestedQuantity();
        return newQuantity;
    }

    public ProductResponse getById(final Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::toResponse)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with ID '%s' not found", id)));
    }

    public List<ProductResponse> getAll() {
        return this.repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
