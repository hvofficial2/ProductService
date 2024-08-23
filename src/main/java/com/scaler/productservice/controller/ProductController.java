package com.scaler.productservice.controller;

import com.scaler.productservice.dto.CategoryDto;
import com.scaler.productservice.dto.CreateProductDto;
import com.scaler.productservice.dto.ProductDto;
import com.scaler.productservice.exception.*;
import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.service.CategoryService;
import com.scaler.productservice.service.ProductService;
import com.scaler.productservice.util.Mapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final Mapper mapper;

    public ProductController(@Qualifier("selfProductService") ProductService productService
            , @Qualifier("selfStoreCategoryService") CategoryService categoryService
            , Mapper mapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.mapper = mapper;
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
        return mapper.mapToProductDto(product);
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
            productDtos.add(mapper.mapToProductDto(product));
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
            productDtos.add(mapper.mapToProductDto(product));
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
            productDtos.add(mapper.mapToProductDto(product));
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
        return mapper.mapToProductDto(product);
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
        return mapper.mapToProductDto(product);
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
        return mapper.mapToProductDto(product);
    }

    @GetMapping("/categories")
    public String[] getAllCategories() {
        log.info("Inside getAllCategories method");
        List<Category> categories = categoryService.getAllCategories();
        if (categories.isEmpty()) {
            log.error("Categories not found");
            throw new CategoriesNotFoundException("Categories not found");
        }
        String[] names = new String[categories.size()];
        int i = 0;
        for (Category category : categories)
            names[i++] = category.getName();
        return names;
    }

    @GetMapping("/products/category/{cat}")
    public List<ProductDto> getProductsInCategory(@PathVariable("cat") String category) {
        log.info("Inside getProductsInCategory method");
        List<Product> products = categoryService.getProductsByCategory(category);
        if (products == null || products.isEmpty()) {
            log.error("No Products found in the given category : " + category);
            throw new ProductNotFoundException("No Products found in the given category : " + category);
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products)
            productDtos.add(mapper.mapToProductDto(product));
        return productDtos;
    }

    @PostMapping("/category")
    public CategoryDto addCategory(@RequestParam("name") String name) {
        log.info("Inside addCategory method");
        if (name == null || name.isEmpty()) {
            log.error("Category name cannot be null or empty");
            throw new InvalidCategoryNameException("Category name cannot be null or empty");
        }

        try {
            return mapper.mapToCategoryDto(categoryService.addCategory(name));
        } catch (Exception e) {
            log.error("Category not added, something went wrong!!");
            log.error(e);
            throw new ProductAddEditDeleteException(e.getMessage());
        }
    }

    @PutMapping("/category")
    public CategoryDto updateCategoryName(@RequestParam("old_name") String oldName, @RequestParam("new_name") String newName) {
        log.info("Inside updateCategoryName method");
        if (oldName == null || oldName.isEmpty() || newName == null || newName.isEmpty()) {
            log.error("Category name cannot be null or empty");
            throw new InvalidCategoryNameException("Category name cannot be null or empty");
        }
        Category category = categoryService.updateCategoryName(oldName, newName);
        if (category == null) {
            log.error("Category does not exist or deleted!!");
            throw new InvalidCategoryNameException("Category does not exist or deleted!!");
        }
        return mapper.mapToCategoryDto(category);
    }

    @DeleteMapping("/category")
    public String deleteCategory(@RequestParam("name") String name) {
        log.info("Inside deleteCategory method");
        if (name == null || name.isEmpty()) {
            log.error("Category name cannot be null or empty");
            throw new InvalidCategoryNameException("Category name cannot be null or empty");
        }
        Category cat = categoryService.deleteCategory(name);
        if (cat == null) {
            log.error("Category does not exist or deleted!!");
            throw new InvalidCategoryNameException("Category does not exist or deleted!!");
        }
        return "Category DELETED!!!";
    }
}
