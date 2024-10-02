package com.workbeatstalent.productservice.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProductGlobalControllerAdvice {

    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<String> notfound(final ProductNotFoundException notFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(notFoundException.getLocalizedMessage());
    }

    @ExceptionHandler(value = {ProductPurchaseException.class})
    public ResponseEntity<String> purchaseError(final ProductPurchaseException purchaseException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(purchaseException.getLocalizedMessage());
    }
}
