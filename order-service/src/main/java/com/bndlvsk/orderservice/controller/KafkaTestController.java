package com.bndlvsk.orderservice.controller;

import com.bndlvsk.orderservice.dto.request.OrderCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderUpdateRequest;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import com.bndlvsk.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka-test")
@RequiredArgsConstructor
@Slf4j
@Profile("test-consumer")
public class KafkaTestController {

    private final OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<OrderResponse> createOrderWithKafka(@RequestBody OrderCreateRequest request) {
        log.info("Received request to create order with Kafka integration: {}", request);
        OrderResponse response = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/update-order/{orderId}")
    public ResponseEntity<OrderResponse> updateOrderWithKafka(
            @PathVariable Long orderId,
            @RequestBody OrderUpdateRequest request) {
        log.info("Received request to update order {} with Kafka integration: {}", orderId, request);
        OrderResponse response = orderService.updateOrder(orderId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete-order/{orderId}")
    public ResponseEntity<Void> deleteOrderWithKafka(@PathVariable Long orderId) {
        log.info("Received request to delete order {} with Kafka integration", orderId);
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
} 