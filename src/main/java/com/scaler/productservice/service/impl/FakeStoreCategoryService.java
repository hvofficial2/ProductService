package com.scaler.productservice.service.impl;

import com.scaler.productservice.dto.FakeProductDto;
import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.service.CategoryService;
import com.scaler.productservice.util.Mapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service("fakeStoreCategoryService")
public class FakeStoreCategoryService implements CategoryService {

    private final RestTemplate restTemplate;
    private final Mapper productMapper;

    public FakeStoreCategoryService(RestTemplate restTemplate, Mapper productMapper) {
        this.restTemplate = restTemplate;
        this.productMapper = productMapper;
    }

    @Override
    public List<Category> getAllCategories() {
        log.info("Inside FakeProductService --> getAllCategories");
        Category[] categories = restTemplate.getForObject("https://fakestoreapi.com/products/categories", Category[].class);
        if(categories == null)  return null;
        List<Category> categoriesList = new ArrayList<>();
        Collections.addAll(categoriesList, categories);
        return categoriesList;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        log.info("Inside FakeProductService --> getProductsByCategory");
        FakeProductDto[] dtos = restTemplate.getForObject("https://fakestoreapi.com/products/category/" + category, FakeProductDto[].class);
        if (dtos == null) return null;
        List<Product> products = new ArrayList<>();
        for (FakeProductDto dto : dtos)
            products.add(productMapper.mapToProduct(dto));
        return products;
    }

    @Override
    public Category addCategory(String categoryName) {
        return null;
    }

    @Override
    public Category getCategoryByName(String name) {
        return null;
    }

    @Override
    public Category deleteCategory(String name) {
        return null;
    }

    @Override
    public Category updateCategoryName(String oldName,String newName) {
        return null;
    }
}
