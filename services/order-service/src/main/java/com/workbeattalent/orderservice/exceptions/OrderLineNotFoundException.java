package com.workbeattalent.orderservice.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderLineNotFoundException extends RuntimeException {
    private final String message;
}
