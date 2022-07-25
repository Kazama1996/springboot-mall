package com.kazama.springbootmall.service.impl;


import com.kazama.springbootmall.constant.ProductCategory;
import com.kazama.springbootmall.dao.ProductDao;
import com.kazama.springbootmall.dto.ProductQueryParams;
import com.kazama.springbootmall.dto.ProductRequest;
import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    public Product getProductById(Integer productId){

        return productDao.getFromProductId(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
         productDao.updateProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {

        productDao.deleteProductById(productId);

    }

    public List<Product> getProducts(ProductQueryParams productQueryParams){
        return productDao.getProducts(productQueryParams);

    }
}
