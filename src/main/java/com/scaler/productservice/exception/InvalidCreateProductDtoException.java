package com.scaler.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCreateProductDtoException extends RuntimeException {
    public InvalidCreateProductDtoException(String message) {
        super(message);
    }
}
