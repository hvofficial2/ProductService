package com.scaler.productservice.repository;

import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.repository.projections.CategoryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
    List<Category> findAllBy();

    Category findByName(String name);

    @Query("select c.name as name,size(c.products) as count from Category c")
    List<CategoryProjection> findAllCategoryWithCountOfProducts();
}
