package com.scaler.productservice.util;

import com.scaler.productservice.dto.FakeProductDto;
import com.scaler.productservice.dto.ProductDto;
import com.scaler.productservice.model.Category;
import com.scaler.productservice.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto mapToProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setCategory(product.getCategory());
        productDto.setImage(product.getImage());
        return productDto;
    }

    public Product mapToProduct(FakeProductDto fakeProductDto) {
        Product product = new Product();
        product.setId(fakeProductDto.getId());
        product.setTitle(fakeProductDto.getTitle());
        product.setDescription(fakeProductDto.getDescription());
        Category cat = new Category();
        cat.setName(fakeProductDto.getCategory());
        product.setCategory(cat);
        product.setImage(fakeProductDto.getImage());
        return product;
    }
}
