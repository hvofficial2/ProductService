package com.scaler.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }
}
