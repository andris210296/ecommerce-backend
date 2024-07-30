package com.example.ecommerce.controller;

import com.example.ecommerce.exception.EcommerceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EcommerceException.class)
    public ProblemDetail handleEcommerceException(EcommerceException ex) {
        return ex.toProblemDetail();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        var fieldErrors = ex.getFieldErrors()
                .stream()
                .map(f -> new InvalidParameter(f.getField(), f.getDefaultMessage()))
                .toList();

        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("Your request parameters didn't validate.");
        pb.setProperty("invalid-parameters", fieldErrors);

        return pb;

    }

    private record InvalidParameter(String field, String message) {
    }
}
