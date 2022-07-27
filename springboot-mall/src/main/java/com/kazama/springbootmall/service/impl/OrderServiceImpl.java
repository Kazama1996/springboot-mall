package com.kazama.springbootmall.service.impl;

import com.kazama.springbootmall.dao.OrderDao;
import com.kazama.springbootmall.dao.ProductDao;
import com.kazama.springbootmall.dto.BuyItem;
import com.kazama.springbootmall.dto.CreateOrderRequest;
import com.kazama.springbootmall.model.OrderItem;
import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;


    @Transactional// 有修改多個table的時候需要+確保資訊一致
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){
        int totalAmount= 0 ;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getFromProductId(buyItem.getProduct_id());
            int amount = product.getPrice() * buyItem.getQuantity();
            totalAmount+= amount;


            //convert buyItem to orderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct_id(buyItem.getProduct_id());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }



        //創建訂單
        Integer order_id =orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(order_id , orderItemList);

        return order_id ;
    }
}
