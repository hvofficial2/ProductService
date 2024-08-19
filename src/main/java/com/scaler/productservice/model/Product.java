package com.scaler.productservice.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private Integer id;
    private String title;
    private double price;
    private String description;
    private String image;
    private Category category;
}
