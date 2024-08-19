package com.scaler.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductAddEditDeleteException extends RuntimeException {
    public ProductAddEditDeleteException(String message) {
        super(message);
    }
}
