package com.rdshinde.microservices.order_service.controller;

import com.rdshinde.microservices.order_service.dto.OrderRequest;
import com.rdshinde.microservices.order_service.dto.OrderResponse;
import com.rdshinde.microservices.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order placed successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse>  getOrders() {
        return orderService.getAllOrders();
    }

}
