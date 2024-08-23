package com.scaler.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Category extends BaseModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", initialValue = 101, allocationSize = 1)
    Integer id;
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category",cascade = {CascadeType.REMOVE})
    private List<Product> products;
}
