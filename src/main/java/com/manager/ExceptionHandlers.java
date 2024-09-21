package com.manager;

import com.ApiResponse.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidatorException(MethodArgumentNotValidException exception){
        // Extract error messages
        List<String> errors = exception.getBindingResult()
                                       .getFieldErrors()
                                       .stream()
                                       .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                                       .collect(Collectors.toList());

        // Print errors for debugging
        return ResponseEntity.badRequest().body(new ApiResponse<>(-1, exception.getMessage(), errors));
    }
}
