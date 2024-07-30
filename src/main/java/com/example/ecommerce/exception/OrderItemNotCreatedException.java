package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class OrderItemNotCreatedException extends EcommerceException {

    private String detail;

    public OrderItemNotCreatedException(String detail) {
        this.detail = detail;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = super.toProblemDetail();
        pb.setTitle("Order Item Not Created");
        pb.setStatus(HttpStatus.NOT_FOUND);
        pb.setDetail(detail);
        return pb;
    }
}
