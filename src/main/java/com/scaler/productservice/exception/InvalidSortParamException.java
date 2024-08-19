package com.scaler.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidSortParamException extends RuntimeException {
    public InvalidSortParamException(String message) {
        super(message);
    }
}
