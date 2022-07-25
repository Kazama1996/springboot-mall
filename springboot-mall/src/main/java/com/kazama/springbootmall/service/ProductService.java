package com.kazama.springbootmall.service;

import com.kazama.springbootmall.dto.ProductRequest;
import com.kazama.springbootmall.model.Product;
import org.springframework.stereotype.Component;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
