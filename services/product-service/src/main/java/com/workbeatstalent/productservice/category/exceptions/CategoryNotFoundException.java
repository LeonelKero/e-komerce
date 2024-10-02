package com.workbeatstalent.productservice.category.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CategoryNotFoundException extends RuntimeException {
    private final String message;
}
