package com.kazama.springbootmall.service;

import com.kazama.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

    public Integer createOrder(Integer userId , CreateOrderRequest createOrderRequest);
}
