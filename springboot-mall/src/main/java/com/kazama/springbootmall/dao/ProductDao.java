package com.kazama.springbootmall.dao;

import com.kazama.springbootmall.model.Product;

public interface ProductDao {

    public Product getFromProductId(Integer productId);
}
