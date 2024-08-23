package com.scaler.productservice.service;

import com.scaler.productservice.model.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(int id);

    List<Product> getAllProducts();

    List<Product> getLimitedProducts(int limit);

    List<Product> getSortedProducts(String sort);

    Product addProduct(String title, double price, String description, String image, String category);

    Product updateProduct(int id, String title, double price, String description, String image, String category);

    Product deleteProduct(int id);

    List<Product> findAllProductsByCategory_NameEquals(String category);
}
