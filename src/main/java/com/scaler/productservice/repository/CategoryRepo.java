package com.scaler.productservice.repository;

import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    public List<Category> findAllBy();

    public Category findByName(String name);
}
