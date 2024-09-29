package com.workbeatstalent.customerservice.customer.exceptions;

import com.workbeatstalent.customerservice.customer.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> apiError(final MethodArgumentNotValidException exception) {
        final var errors = new HashMap<String, String>();
        exception.getBindingResult().getAllErrors()
                .forEach(objectError -> {
                    final var field = ((FieldError) objectError).getField();
                    final var error = objectError.getDefaultMessage();
                    errors.put(field, error);
                });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

}
