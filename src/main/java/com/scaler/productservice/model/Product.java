package com.scaler.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@ToString
@Entity
public class Product extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", initialValue = 1, allocationSize = 1)
    private Integer id;
    private String title;
    private double price;
    private String description;
    private String image;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Category category;
}
