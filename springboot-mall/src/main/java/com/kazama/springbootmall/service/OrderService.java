package com.kazama.springbootmall.service;

import com.kazama.springbootmall.dto.CreateOrderRequest;
import com.kazama.springbootmall.model.Order;
import com.kazama.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderService {

    public Integer createOrder(Integer userId , CreateOrderRequest createOrderRequest);

    public Order getOrderById(Integer orderId);
}
