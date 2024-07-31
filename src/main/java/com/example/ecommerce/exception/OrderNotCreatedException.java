package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class OrderNotCreatedException extends EcommerceException {

    private final String detail;

    public OrderNotCreatedException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = super.toProblemDetail();
        pb.setTitle("Order Not Created");
        pb.setStatus(HttpStatus.NOT_FOUND);
        pb.setDetail(detail);
        return pb;
    }
}
