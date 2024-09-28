package com.workbeatstalent.customerservice.customer.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CustomerEmailAlreadyUsedException extends RuntimeException {
    private final String msg;
}
