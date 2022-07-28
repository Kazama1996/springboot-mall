package com.kazama.springbootmall.controller;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.kazama.springbootmall.dto.CreateOrderRequest;
import com.kazama.springbootmall.dto.OrderQueryParams;
import com.kazama.springbootmall.model.Order;
import com.kazama.springbootmall.model.OrderItem;
import com.kazama.springbootmall.service.OrderService;
import com.kazama.springbootmall.utill.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                        @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer order_id = orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(order_id);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(@PathVariable Integer userId,
                                                 @RequestParam(defaultValue = "10")@Max(1000) @Min(0) Integer limit,
                                                 @RequestParam(defaultValue = "0") @Min(0) Integer offset){

        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setLimit(limit);
        orderQueryParams.setUserId(userId);
        orderQueryParams.setOffset(offset);

        List<Order> orderList = orderService.getOrders(orderQueryParams);

        Integer count = orderService.countOrder(orderQueryParams);


        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResults(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);


    }


}
