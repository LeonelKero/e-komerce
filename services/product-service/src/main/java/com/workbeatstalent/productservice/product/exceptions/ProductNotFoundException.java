package com.workbeatstalent.productservice.product.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductNotFoundException extends RuntimeException{
    private final String message;
}
