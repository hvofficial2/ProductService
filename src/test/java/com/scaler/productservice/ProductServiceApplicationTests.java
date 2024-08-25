package com.scaler.productservice;

import com.scaler.productservice.repository.ProductRepo;
import com.scaler.productservice.repository.projections.ProductProjection;
import com.scaler.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceApplicationTests {

    @Autowired
    ProductRepo productRepo;

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

}
