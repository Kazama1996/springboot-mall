package com.kazama.springbootmall.dao;


import com.kazama.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    public Integer createOrder(Integer userId ,  Integer totalAmount);

    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
