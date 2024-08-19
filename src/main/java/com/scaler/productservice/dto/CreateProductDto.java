package com.scaler.productservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateProductDto {
    private String title; // YES
    private double price; // YES
    private String description; // YES
    private String image; // discuss this later on why to add (null)
    private String category; // YES
}

