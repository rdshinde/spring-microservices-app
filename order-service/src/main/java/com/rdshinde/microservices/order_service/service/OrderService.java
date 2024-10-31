package com.rdshinde.microservices.order_service.service;

import com.rdshinde.microservices.order_service.client.InventoryClient;
import com.rdshinde.microservices.order_service.dto.OrderRequest;
import com.rdshinde.microservices.order_service.dto.OrderResponse;
import com.rdshinde.microservices.order_service.event.OrderPlacedEvent;
import com.rdshinde.microservices.order_service.model.Order;
import com.rdshinde.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (!isProductInStock) {
            log.error("Product with skuCode: {} is not in stock", orderRequest.skuCode());
            throw new RuntimeException("Product is not in stock");
        }else {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setQuantity(orderRequest.quantity());
            order.setSkuCode(orderRequest.skuCode());
            orderRepository.save(order);
            log.info("Order placed successfully: {}", order);

            //Send message to kakfa
            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(), orderRequest.userDetails().email());
            log.info("Start - Sending order placed event to kafka: {}", orderPlacedEvent);
            kafkaTemplate.send("order-service", orderPlacedEvent);
            log.info("End - Order placed event sent successfully:{}", orderPlacedEvent);
        }
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderResponse(order.getId(), order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity()))
                .toList();
    }
}
