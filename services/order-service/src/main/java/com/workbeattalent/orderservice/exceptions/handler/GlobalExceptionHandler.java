package com.workbeattalent.orderservice.exceptions.handler;

import com.workbeattalent.orderservice.exceptions.BusinessException;
import com.workbeattalent.orderservice.exceptions.OrderLineNotFoundException;
import com.workbeattalent.orderservice.exceptions.OrderNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<String> unableToPerformOrder(final BusinessException bex) {
        return ResponseEntity
                .badRequest()
                .body(bex.getMessage());
    }

    @ExceptionHandler(value = {OrderNotFoundException.class})
    public ResponseEntity<String> orderNotFoundHandler(final OrderNotFoundException oex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(oex.getMessage());
    }

    @ExceptionHandler(value = {OrderLineNotFoundException.class})
    public ResponseEntity<String> orderLineNotFoundHandler(final OrderLineNotFoundException olex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(olex.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> valiationErrorHandler(final MethodArgumentNotValidException validException) {
        final var errors = new HashMap<String, String>();
        validException.getBindingResult()
                .getAllErrors()
                .forEach(error -> {
                    errors.put(((FieldError) error).getField(), error.getDefaultMessage());
                });
        return ResponseEntity.badRequest().body(new ErrorResponse(errors));
    }
}
