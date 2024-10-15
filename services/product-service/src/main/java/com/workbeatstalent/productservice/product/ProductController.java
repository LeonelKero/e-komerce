package com.workbeatstalent.productservice.product;

import com.workbeatstalent.productservice.product.dto.ProductPurchaseRequest;
import com.workbeatstalent.productservice.product.dto.ProductPurchaseResponse;
import com.workbeatstalent.productservice.product.dto.ProductRequest;
import com.workbeatstalent.productservice.product.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {"/api/v1/products"})
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Integer> addProduct(final @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(request));
    }

    @PostMapping(path = {"/purchases"})
    public ResponseEntity<List<ProductPurchaseResponse>> allPurchases(final @RequestBody List<ProductPurchaseRequest> purchaseRequest) {
        return ResponseEntity.ok(this.service.purchases(purchaseRequest));
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<ProductResponse> getById(final @PathVariable Integer id) {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> allProducts() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping(path = {"/count"})
    public ResponseEntity<Long> countProducts() {
        return ResponseEntity.ok(this.service.countAllProducts());
    }
}
