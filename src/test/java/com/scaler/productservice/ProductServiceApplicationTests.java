package com.scaler.productservice;

import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import com.scaler.productservice.repository.CategoryRepo;
import com.scaler.productservice.repository.ProductRepo;
import com.scaler.productservice.repository.projections.ProductProjection;
import com.scaler.productservice.service.ProductService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo categoryRepo;

    @Test
    void contextLoads() {
    }

    @Test
    void productProjectionTest(){
        List<ProductProjection> p = productRepo.findAllProductPrice();
        for(ProductProjection pp : p){
            System.out.println(pp.getTitle() + " " + pp.getPrice());
        }
    }

    @Test
    @Transactional
    void testLazyLoading(){
        Category category = categoryRepo.findByName("electronics3");
        System.out.println(category.getId());
        System.out.println("##########");
        System.out.println("##########");
        System.out.println("##########");
        for(Product p : category.getProducts())
            System.out.println(p.getTitle() + " " + p.getPrice());
    }

}
