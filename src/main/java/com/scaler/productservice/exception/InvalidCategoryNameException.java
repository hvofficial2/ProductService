package com.scaler.productservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCategoryNameException extends RuntimeException{
    public InvalidCategoryNameException(String s) {
        super(s);
    }
}
