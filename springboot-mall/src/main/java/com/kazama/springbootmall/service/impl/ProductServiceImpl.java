package com.kazama.springbootmall.service.impl;


import com.kazama.springbootmall.dao.ProductDao;
import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    public Product getProductById(Integer productId){
        System.out.println("OK service");

        return productDao.getFromProductId(productId);
    }
}
