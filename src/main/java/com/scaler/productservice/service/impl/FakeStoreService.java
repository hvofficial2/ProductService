package com.scaler.productservice.service.impl;

import com.scaler.productservice.dto.FakeProductDto;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.service.ProductService;
import com.scaler.productservice.util.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class FakeStoreService implements ProductService {

    private final RestTemplate restTemplate;
    private final ProductMapper productMapper;

    public FakeStoreService(RestTemplate restTemplate, ProductMapper productMapper) {
        this.restTemplate = restTemplate;
        this.productMapper = productMapper;
    }

    @Override
    public Product getProductById(int id) {
        log.info("Inside FakeProductService --> getProductById");
        FakeProductDto fakeProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeProductDto.class);
        if (fakeProductDto == null) return null;
        return productMapper.mapToProduct(fakeProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        log.info("Inside FakeProductService --> getAllProducts");
        FakeProductDto[] dtos = restTemplate.getForObject("https://fakestoreapi.com/products", FakeProductDto[].class);
        if (dtos == null) return null;
        List<Product> products = new ArrayList<>();
        for (FakeProductDto dto : dtos) {
            products.add(productMapper.mapToProduct(dto));
        }
        return products;
    }

    @Override
    public List<Product> getLimitedProducts(int limit) {
        log.info("Inside FakeProductService --> getLimitedProducts");
        FakeProductDto[] dtos = restTemplate.getForObject("https://fakestoreapi.com/products?limit=" + limit, FakeProductDto[].class);
        if (dtos == null) return null;
        List<Product> products = new ArrayList<>();
        for (FakeProductDto dto : dtos) {
            products.add(productMapper.mapToProduct(dto));
        }
        return products;
    }

    public List<Product> getSortedProducts(String sort) {
        log.info("Inside FakeProductService --> getSortedProducts");
        FakeProductDto[] dtos = restTemplate.getForObject("https://fakestoreapi.com/products?sort=" + sort, FakeProductDto[].class);
        if (dtos == null) return null;
        List<Product> products = new ArrayList<>();
        for (FakeProductDto dto : dtos) {
            products.add(productMapper.mapToProduct(dto));
        }
        return products;
    }

    @Override
    public Product addProduct(String title, double price, String description, String image, String category) {
        log.info("Inside FakeProductService --> addProduct");
        FakeProductDto requestDto = new FakeProductDto();
        requestDto.setTitle(title);
        requestDto.setPrice(price);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setCategory(category);

        FakeProductDto responseDto = restTemplate.postForObject("https://fakestoreapi.com/products", requestDto, FakeProductDto.class);
        if (responseDto == null) return null;
        return productMapper.mapToProduct(responseDto);
    }

    @Override
    public Product updateProduct(int id, String title, double price, String description, String image, String category) {
        log.info("Inside FakeProductService --> updateProduct");
        FakeProductDto requestDto = restTemplate.getForObject("https://fakestoreapi.com/products/" + id, FakeProductDto.class);
        requestDto.setTitle(title);
        requestDto.setPrice(price);
        requestDto.setDescription(description);
        requestDto.setImage(image);
        requestDto.setCategory(category);

//        FakeProductDto dto = restTemplate.patchForObject("https://fakestoreapi.com/products/"+id, requestDto, FakeProductDto.class);
//                restTemplate.patchForObject("https://fakestoreapi.com/products/7", requestDto,FakeProductDto.class);
        ResponseEntity<FakeProductDto> response = restTemplate.exchange("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, new HttpEntity<>(requestDto), FakeProductDto.class);
        return (response.getBody() != null) ? productMapper.mapToProduct(response.getBody()) : null;
    }

    @Override
    public Product deleteProduct(int id) {
        log.info("Inside FakeProductService --> deleteProduct");
        FakeProductDto dto = restTemplate.exchange("https://fakestoreapi.com/products/" + id, HttpMethod.DELETE, null, FakeProductDto.class).getBody();
        return (dto != null) ? productMapper.mapToProduct(dto) : null;
    }

    @Override
    public String[] getAllCategories() {
        log.info("Inside FakeProductService --> getAllCategories");
        return restTemplate.getForObject("https://fakestoreapi.com/products/categories", String[].class);
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
}
