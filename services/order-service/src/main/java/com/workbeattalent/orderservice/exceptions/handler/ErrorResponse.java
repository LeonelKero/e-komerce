package com.workbeattalent.orderservice.exceptions.handler;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
