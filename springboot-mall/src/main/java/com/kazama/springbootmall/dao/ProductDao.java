package com.kazama.springbootmall.dao;

import com.kazama.springbootmall.constant.ProductCategory;
import com.kazama.springbootmall.dto.ProductQueryParams;
import com.kazama.springbootmall.dto.ProductRequest;
import com.kazama.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    public Product getFromProductId(Integer productId);

    public Integer createProduct(ProductRequest productRequest);

    public void updateProduct(Integer productId, ProductRequest productRequest);

    public void deleteProductById(Integer productId);

    public void updateStock(Integer producId , Integer updatedValue);

    public Integer countProducts(ProductQueryParams productQueryParams);

    public List<Product> getProducts(ProductQueryParams productQueryParams);



}