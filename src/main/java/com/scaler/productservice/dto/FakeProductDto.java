package com.scaler.productservice.dto;


import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
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
