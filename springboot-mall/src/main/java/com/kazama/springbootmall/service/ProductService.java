package com.kazama.springbootmall.service;

import com.kazama.springbootmall.constant.ProductCategory;
import com.kazama.springbootmall.dto.ProductQueryParams;
import com.kazama.springbootmall.dto.ProductRequest;
import com.kazama.springbootmall.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);


}
