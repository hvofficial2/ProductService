package com.scaler.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CategoriesNotFoundException extends RuntimeException {
    public CategoriesNotFoundException(String message) {
        super(message);
    }
}
