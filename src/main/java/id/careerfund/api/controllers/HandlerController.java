package id.careerfund.api.controllers;

import id.careerfund.api.domains.models.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

public class HandlerController {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse<?> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        HashMap<String, HashMap<String, String>> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            HashMap<String, String> value = new HashMap<>();

            value.put("code", error.getCode());
            value.put("message", errorMessage);
            errors.put(fieldName, value);
        });
        return new ValidationErrorResponse<>(errors);
    }
}
