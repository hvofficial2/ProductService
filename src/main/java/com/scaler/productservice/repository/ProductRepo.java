package com.scaler.productservice.repository;

import com.scaler.productservice.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

    Product findById(int id);

    List<Product> findAllBy();

    @Query(value = "SELECT * FROM product LIMIT ?1", nativeQuery = true)
    List<Product> findAllWithLimit(int limit);

    List<Product> findByOrderById();

    List<Product> findByOrderByIdDesc();

    List<Product> findAllProductsByCategory_NameEquals(String category);
}
