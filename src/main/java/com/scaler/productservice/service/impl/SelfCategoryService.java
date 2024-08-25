package com.scaler.productservice.service.impl;

import com.scaler.productservice.exception.InvalidCategoryNameException;
import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.repository.CategoryRepo;
import com.scaler.productservice.repository.projections.CategoryProjection;
import com.scaler.productservice.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service("selfStoreCategoryService")
public class SelfCategoryService implements CategoryService {

    private final CategoryRepo categoryRepo;

    public SelfCategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Inside SelfProductService --> getAllCategories");
        return categoryRepo.findAllBy();
    }

    @Override
    public List<Product> getProductsByCategory(String name) {
        log.info("Inside SelfProductService --> getProductsByCategory");
        Category category = categoryRepo.findByName(name);
        if(category == null || category.isDeleted()) {
            log.error("Category with name {} not found or deleted", name);
            throw new InvalidCategoryNameException(name+" category does not exists!!!");
        }
        return category.getProducts();
    }

    @Override
    public Category addCategory(String name) {
        log.info("Inside SelfProductService --> addCategory");
        Category category = categoryRepo.findByName(name);
        if(category != null && category.isDeleted()){
            category.setLastUpdatedAt(new Date());
            category.setDeleted(false);
            return categoryRepo.save(category);
        }
        else if(category != null && !category.isDeleted()) {
            throw new RuntimeException("Category with name " + name + " already exists");
        }
        category = new Category();
        category.setName(name);
        category.setCreatedAt(new Date());
        category.setLastUpdatedAt(new Date());
        category.setDeleted(false);
        return categoryRepo.save(category);
    }

    @Override
    public Category getCategoryByName(String name) {
        log.info("Inside SelfProductService --> getCategoryByName");
        return categoryRepo.findByName(name);
    }

    @Override
    public Category deleteCategory(String name) {
        log.info("Inside SelfProductService --> deleteCategory");
        Category category = categoryRepo.findByName(name);
        if(category == null || category.isDeleted()) return null;
        category.setDeleted(true);
        category.setLastUpdatedAt(new Date());
        categoryRepo.save(category);
        return category;
    }

    @Override
    public Category updateCategoryName(String oldName,String newName) {
        log.info("Inside SelfProductService --> updateCategoryName");
        Category category = categoryRepo.findByName(oldName);
        if(category == null || category.isDeleted()) return null;
        category.setName(newName);
        category.setLastUpdatedAt(new Date());
        categoryRepo.save(category);
        return category;
    }

    @Override
    public List<CategoryProjection> getCategoryProjections() {
        log.info("Inside SelfProductService --> getCategoryProjections");
        return categoryRepo.findAllCategoryWithCountOfProducts();
    }
}
