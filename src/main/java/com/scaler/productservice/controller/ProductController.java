package com.scaler.productservice.controller;

import com.scaler.productservice.dto.CreateProductDto;
import com.scaler.productservice.dto.ProductDto;
import com.scaler.productservice.exception.*;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.service.ProductService;
import com.scaler.productservice.util.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductController(ProductService productService, ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable Integer id) {
        if (!isValid(id)) {
            log.error("Invalid product id");
            throw new InvalidProductIdException("Invalid product id : " + id);
        }

        log.info("Inside getProductByid method for id : " + id);
        Product product = productService.getProductById(id);
        if (product == null)
            throw new ProductNotFoundException("Product not found with the given id : " + id);
        return productMapper.mapToProductDto(product);
    }

    public boolean isValid(Integer id) {
        return id != null && id > 0;
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts() {
        log.info("Inside getAllProducts method");
        List<Product> products = productService.getAllProducts();
        if (products == null || products.isEmpty())
            throw new ProductNotFoundException("Products not found");
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products)
            productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }

    @GetMapping("/limitedProducts")
    public List<ProductDto> getLimitedProducts(@RequestParam("limit") Integer limit) {
        log.info("Inside getLimitedProducts method with limit : " + limit);
        if (limit == 0)
            return getAllProducts();

        if (!isValid(limit)) {
            log.error("Invalid limit!");
            throw new InvalidLimitException("Invalid limit : " + limit);
        }
        List<Product> products = productService.getLimitedProducts(limit);
        if (products == null || products.isEmpty()) {
            log.error("Products not found");
            throw new ProductNotFoundException("Products not found");
        }

        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products)
            productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }

    @GetMapping("/sortedProducts")
    public List<ProductDto> getSortedProducts(@RequestParam("sort") String sort) {
        log.info("Inside getSortedProducts method with sort : " + sort);
        if ((sort == null || sort.isEmpty()) || (!sort.equalsIgnoreCase("asc") && !sort.equalsIgnoreCase("desc"))) {
            log.error("Invalid sort param");
            throw new InvalidSortParamException("Invalid sort parameter given : " + sort);
        }

        List<Product> products = productService.getSortedProducts(sort.toLowerCase());
        if (products == null || products.isEmpty()) {
            log.error("Products not found");
            throw new ProductNotFoundException("Products not found");
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products)
            productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }

    @PostMapping("/product")
    public ProductDto addProduct(@RequestBody CreateProductDto createProductDto) {
        log.info("Inside addProduct method");
        if (createProductDto == null) {
            log.error("Product dto cannot be null!!");
            throw new InvalidCreateProductDtoException("Product dto cannot be null!!");
        }
        Product product = productService.addProduct(createProductDto.getTitle()
                , createProductDto.getPrice()
                , createProductDto.getDescription()
                , createProductDto.getImage()
                , createProductDto.getCategory());
        if (product == null) {
            log.error("Product not added, something went wrong!!");
            throw new ProductAddEditDeleteException("Product not added, something went wrong!!");
        }
        return productMapper.mapToProductDto(product);
    }

    @PutMapping("/product/{id}")
    public ProductDto updateProduct(@PathVariable("id") Integer id, @RequestBody CreateProductDto createProductDto) {
        log.info("Inside updateProduct method");
        if (!isValid(id)) {
            log.error("Invalid product id");
            throw new InvalidProductIdException("Invalid product id : " + id);
        }

        if (productService.getProductById(id) == null) {
            log.error("Product not found");
            throw new ProductNotFoundException("Product not found with the given id : " + id);
        }

        Product product = productService.updateProduct(id
                , createProductDto.getTitle()
                , createProductDto.getPrice()
                , createProductDto.getDescription()
                , createProductDto.getImage()
                , createProductDto.getCategory());
        if (product == null) {
            log.error("Product not updated, something went wrong!!");
            throw new ProductAddEditDeleteException("Product not updated, something went wrong!!");
        }
        return productMapper.mapToProductDto(product);
    }

    @DeleteMapping("/product/{id}")
    public ProductDto deleteProduct(@PathVariable("id") Integer id) {
        log.info("Inside deleteProduct method with id : " + id);
        if (!isValid(id)) {
            log.error("Invalid product id");
            throw new InvalidProductIdException("Invalid product id : " + id);
        }
        if (productService.getProductById(id) == null) {
            log.error("Product not found");
            throw new ProductNotFoundException("Product not found with the given id : " + id);
        }

        Product product = productService.deleteProduct(id);
        if (product == null) {
            log.error("Product not deleted, something went wrong!!");
            throw new ProductAddEditDeleteException("Product not deleted, something went wrong!!");
        }
        return productMapper.mapToProductDto(product);
    }

    @GetMapping("/categories")
    public String[] getAllCategories() {
        log.info("Inside getAllCategories method");
        String[] categories = productService.getAllCategories();
        if (categories == null || categories.length == 0) {
            log.error("Categories not found");
            throw new CategoriesNotFoundException("Categories not found");
        } else
            return categories;
    }

    @GetMapping("/products/category/{cat}")
    public List<ProductDto> getProductsInCategory(@PathVariable("cat") String category) {
        log.info("Inside getProductsInCategory method");
        List<Product> products = productService.getProductsByCategory(category);
        if (products == null || products.isEmpty()) {
            log.error("No Products found in the given category : " + category);
            throw new ProductNotFoundException("No Products found in the given category : " + category);
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products)
            productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }
}
