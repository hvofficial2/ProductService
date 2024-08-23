package com.scaler.productservice.service;

import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    Category addCategory(String name);

    List<Product> getProductsByCategory(String category);

    Category getCategoryByName(String name);

    Category deleteCategory(String name);

    Category updateCategoryName(String oldName,String newName);
}
