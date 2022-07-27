package com.kazama.springbootmall.controller;

import com.kazama.springbootmall.dto.CreateOrderRequest;
import com.kazama.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("user/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                        @RequestBody @Valid CreateOrderRequest createOrderRequest){


        Integer order_id = orderService.createOrder(userId,createOrderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(order_id);
    }


}
