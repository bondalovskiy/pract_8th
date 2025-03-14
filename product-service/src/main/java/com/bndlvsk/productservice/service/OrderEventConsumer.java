package com.bndlvsk.productservice.service;

import com.bndlvsk.productservice.event.OrderEvent;
import com.bndlvsk.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final ProductService productService;

    @KafkaListener(topics = "${kafka.topic.order-created:pizza-order-created}", groupId = "product-service-group")
    public void handleOrderCreatedEvent(OrderEvent event) {
        log.info("Received order created event: {}", event);
        event.getItems().forEach(item -> {
            // Update product inventory or handle the order creation as needed
            productService.handleOrderCreated(item.getProductId(), item.getQuantity());
        });
    }

    @KafkaListener(topics = "${kafka.topic.order-updated:pizza-order-updated}", groupId = "product-service-group")
    public void handleOrderUpdatedEvent(OrderEvent event) {
        log.info("Received order updated event: {}", event);
        event.getItems().forEach(item -> {
            // Update product inventory or handle the order update as needed
            productService.handleOrderUpdated(item.getProductId(), item.getQuantity());
        });
    }

    @KafkaListener(topics = "${kafka.topic.order-deleted:pizza-order-deleted}", groupId = "product-service-group")
    public void handleOrderDeletedEvent(OrderEvent event) {
        log.info("Received order deleted event for order ID: {}", event.getOrderId());
        if (event.getItems() != null) {
            event.getItems().forEach(item -> {
                // Restore product inventory or handle the order deletion as needed
                productService.handleOrderDeleted(item.getProductId(), item.getQuantity());
            });
        }
    }
} 