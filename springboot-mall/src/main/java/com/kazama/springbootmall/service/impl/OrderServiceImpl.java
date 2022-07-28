package com.kazama.springbootmall.service.impl;

import com.kazama.springbootmall.dao.OrderDao;
import com.kazama.springbootmall.dao.ProductDao;
import com.kazama.springbootmall.dao.UserDao;
import com.kazama.springbootmall.dto.BuyItem;
import com.kazama.springbootmall.dto.CreateOrderRequest;
import com.kazama.springbootmall.model.Order;
import com.kazama.springbootmall.model.OrderItem;
import com.kazama.springbootmall.model.Product;
import com.kazama.springbootmall.model.User;
import com.kazama.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {


    private final static  Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;


    @Transactional// 有修改多個table的時候需要+確保資訊一致
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){
        User user = userDao.getUserById(userId);

        if(user==null){
            log.warn("user: {} 不存在  ", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        int totalAmount= 0 ;
        List<OrderItem> orderItemList = new ArrayList<>();

        for(BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getFromProductId(buyItem.getProduct_id());

            if(product==null){
                log.warn("商品 : {} 不存在", buyItem.getProduct_id());
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if(product.getStock() < buyItem.getQuantity()){
                log.warn("商品 : {} 庫存不足 無法購買 。庫存剩餘 : {}，欲購買數量 : {}" , product.getProduct_id(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            productDao.updateStock(product.getProduct_id(), product.getStock() -buyItem.getQuantity());

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

    @Override
    public Order getOrderById(Integer orderId) {


        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);




        order.setOrderItemList(orderItemList);



        return order;
    }
}
