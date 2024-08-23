package com.scaler.productservice.service.impl;

import com.scaler.productservice.exception.CategoryNotFoundException;
import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.repository.CategoryRepo;
import com.scaler.productservice.repository.ProductRepo;
import com.scaler.productservice.service.CategoryService;
import com.scaler.productservice.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j2
@Service("selfProductService")
public class SelfProductService implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryService categoryService;
    private final CategoryRepo categoryRepo;


    public SelfProductService(ProductRepo productRepo
            , @Qualifier("selfStoreCategoryService") CategoryService categoryService, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryService = categoryService;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product getProductById(int id) {
        log.info("Inside SelfProductService --> Get product by id: " + id);
        return productRepo.findById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Inside SelfProductService --> Get all products");
        return productRepo.findAllBy();
    }

    @Override
    public List<Product> getLimitedProducts(int limit) {
        log.info("Inside SelfProductService --> Get limited products");
        return productRepo.findAllWithLimit(limit);
    }

    @Override
    public List<Product> getSortedProducts(String sort) {
        log.info("Inside SelfProductService --> Get sorted products");
        return (sort.equals("desc"))? productRepo.findByOrderByIdDesc() : productRepo.findByOrderById();
    }

    @Override
    public Product addProduct(String title, double price, String description, String image, String category) {
        log.info("Inside SelfProductService --> Add product");
        Category categoryObj = categoryService.getCategoryByName(category);
        if(categoryObj == null)
            categoryObj = categoryService.addCategory(category);
        else if(categoryObj.isDeleted()){
            categoryObj.setDeleted(false);
            categoryObj.setLastUpdatedAt(new Date());
            categoryObj = categoryRepo.save(categoryObj);
        }

        Product requestDto = new Product();
        requestDto.setTitle(title);
        requestDto.setPrice(price);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setDeleted(false);
        requestDto.setCreatedAt(new Date());
        requestDto.setLastUpdatedAt(new Date());
        requestDto.setCategory(categoryObj);

        return productRepo.save(requestDto);
    }

    @Override
    public Product updateProduct(int id, String title, double price, String description, String image, String category) {
        log.info("Inside SelfProductService --> Update product");
        Category categoryObj = categoryService.getCategoryByName(category);
        if(categoryObj == null)
            categoryObj = categoryService.addCategory(category);
        else if(categoryObj.isDeleted()){
            categoryObj.setDeleted(false);
            categoryObj.setLastUpdatedAt(new Date());
            categoryObj = categoryRepo.save(categoryObj);
        }

        Product requestDto = productRepo.findById(id);
        requestDto.setTitle(title);
        requestDto.setPrice(price);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setDeleted(false);
        requestDto.setCreatedAt(new Date());
        requestDto.setLastUpdatedAt(new Date());
        requestDto.setCategory(categoryObj);

        return productRepo.save(requestDto);
    }

    @Override
    public Product deleteProduct(int id) {
        log.info("Inside SelfProductService --> Delete product");
        Product requestDto = productRepo.findById(id);
        requestDto.setDeleted(true);
        requestDto.setLastUpdatedAt(new Date());
        return productRepo.save(requestDto);
    }

    @Override
    public List<Product> findAllProductsByCategory_NameEquals(String category) {
        log.info("Inside SelfProductService --> Find all products by category");
        return productRepo.findAllProductsByCategory_NameEquals(category);
    }
}
