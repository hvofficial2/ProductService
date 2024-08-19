package com.scaler.productservice.dto;

import com.scaler.productservice.model.Category;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDto {
    private Integer id;
    private String title;
    private String description;
    private String image;
    private Category category;
}
