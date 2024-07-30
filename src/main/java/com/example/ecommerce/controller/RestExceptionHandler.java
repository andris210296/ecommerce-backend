package com.example.ecommerce.controller;

import com.example.ecommerce.exception.EcommerceException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(EcommerceException.class)
    public ProblemDetail handleEcommerceException(EcommerceException ex) {
        return ex.toProblemDetail();
    }
}
