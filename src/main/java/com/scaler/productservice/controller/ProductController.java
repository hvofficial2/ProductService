package com.scaler.productservice.controller;

import com.scaler.productservice.dto.CreateProductDto;
import com.scaler.productservice.dto.ProductDto;
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

    public ProductController(ProductService productService,ProductMapper productMapper) {
        this.productService = productService;
        this.productMapper = productMapper;
    }

    @GetMapping("/product/{id}")
    public ProductDto getProductById(@PathVariable Integer id){
        if(!isValid(id))
        {
            log.error("Invalid product id");
            return null;
        }

        log.info("Inside getProductByid method for id : "+id);
        Product product = productService.getProductById(id);
        if(product == null) return null;
        return productMapper.mapToProductDto(product);
    }

    public boolean isValid(Integer id){
        return id != null && id > 0;
    }

    @GetMapping("/products")
    public List<ProductDto> getAllProducts(){
        log.info("Inside getAllProducts method");
        List<Product> products = productService.getAllProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        if(products != null && !products.isEmpty())
            for(Product product : products)
                productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }

    @GetMapping("/limitedProducts")
    public List<ProductDto> getLimitedProducts(@RequestParam("limit") Integer limit){
        log.info("Inside getLimitedProducts method with limit : "+limit);
        if(limit==0)
            return getAllProducts();

        if(!isValid(limit)){
            log.error("Invalid limit!");
            return null;
        }
        List<Product> products = productService.getLimitedProducts(limit);
        List<ProductDto> productDtos = new ArrayList<>();
        if(products != null && !products.isEmpty())
            for(Product product : products)
                productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }

    @GetMapping("/sortedProducts")
    public List<ProductDto> getSortedProducts(@RequestParam("sort") String sort){
        log.info("Inside getSortedProducts method with sort : "+sort);
        if((sort == null || sort.isEmpty()) || (!sort.equalsIgnoreCase("asc") && !sort.equalsIgnoreCase("desc"))){
            log.error("Invalid sort param");
            return null;
        }

        List<Product> products = productService.getSortedProducts(sort.toLowerCase());
        List<ProductDto> productDtos = new ArrayList<>();
        if(products != null && !products.isEmpty())
            for(Product product : products)
                productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }

    @PostMapping("/product")
    public ProductDto addProduct(@RequestBody CreateProductDto createProductDto){
        log.info("Inside addProduct method");
        Product product = productService.addProduct(createProductDto.getTitle()
                ,createProductDto.getPrice()
                ,createProductDto.getDescription()
                ,createProductDto.getImage()
                ,createProductDto.getCategory());
        return (product != null) ? productMapper.mapToProductDto(product) : null;
    }

    @PutMapping("/product/{id}")
    public ProductDto updateProduct(@PathVariable("id")Integer id,@RequestBody CreateProductDto createProductDto){
        log.info("Inside updateProduct method");
        if(!isValid(id)){
            log.error("Invalid product id");
            return null;
        }
        Product product = productService.updateProduct(id
                ,createProductDto.getTitle()
                ,createProductDto.getPrice()
                ,createProductDto.getDescription()
                ,createProductDto.getImage()
                ,createProductDto.getCategory());
        if(product == null) return null;
        return productMapper.mapToProductDto(product);
    }

    @DeleteMapping("/product/{id}")
    public ProductDto deleteProduct(@PathVariable("id")Integer id){
        log.info("Inside deleteProduct method with id : "+id);
        if(!isValid(id)){
            log.error("Invalid product id");
            return null;
        }
        Product product = productService.deleteProduct(id);
        return (product == null)? null:productMapper.mapToProductDto(product);
    }

    @GetMapping("/categories")
    public String[] getAllCategories(){
        log.info("Inside getAllCategories method");
        String[] categories = productService.getAllCategories();
        if(categories == null || categories.length == 0){
            log.error("Categories is empty");
            return null;
        }
        else
            return categories;
    }

    @GetMapping("/products/category/{cat}")
    public List<ProductDto> getProductsInCategory(@PathVariable("cat")String category){
        log.info("Inside getProductsInCategory method");
        List<Product> products = productService.getProductsByCategory(category);
        if(products == null || products.isEmpty()){
            log.error("No Products found in the given category : " + category);
            return null;
        }
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products)
            productDtos.add(productMapper.mapToProductDto(product));
        return productDtos;
    }
}
