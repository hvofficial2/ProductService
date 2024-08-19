package com.scaler.productservice.service;

import com.scaler.productservice.model.Product;

import java.util.List;

public interface ProductService {
    public Product getProductById(int id);

    public List<Product> getAllProducts();

    public List<Product> getLimitedProducts(int limit);

    public List<Product> getSortedProducts(String sort);

    public Product addProduct(String title, double price, String description, String image, String category);

    public Product updateProduct(int id, String title, double price, String description, String image, String category);

    public Product deleteProduct(int id);

    public String[] getAllCategories();

    public List<Product> getProductsByCategory(String category);
}
