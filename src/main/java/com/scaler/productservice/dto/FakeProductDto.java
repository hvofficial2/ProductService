package com.scaler.productservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class FakeProductDto {
    private Integer id;
    private String title;
    private double price;
    private String description;
    private String image;
    private String category;
}
