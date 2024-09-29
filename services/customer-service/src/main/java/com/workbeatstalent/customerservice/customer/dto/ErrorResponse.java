package com.workbeatstalent.customerservice.customer.dto;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
