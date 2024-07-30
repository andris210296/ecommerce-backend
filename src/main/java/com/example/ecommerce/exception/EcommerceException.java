package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class EcommerceException extends RuntimeException {

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Ecommerce Internal Server Error");

        return pb;
    }
}
