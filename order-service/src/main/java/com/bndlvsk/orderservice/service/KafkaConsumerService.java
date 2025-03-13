package com.bndlvsk.orderservice.service;

import com.bndlvsk.orderservice.dto.event.OrderEvent;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "order-events", groupId = "order-service-group")
    public void handleOrderEvent(OrderEvent event) {
        try {
            log.info("Received order event: {} with data: {}", event.getEventType(), event.getOrder());
            // Handle different event types here
            switch (event.getEventType()) {
                case "ORDER_CREATED" -> handleOrderCreated(event.getOrder());
                case "ORDER_UPDATED" -> handleOrderUpdated(event.getOrder());
                case "ORDER_DELETED" -> handleOrderDeleted(event.getOrder());
                default -> log.warn("Unknown event type: {}", event.getEventType());
            }
        } catch (Exception e) {
            log.error("Error processing order event: {}", event.getEventType(), e);
        }
    }

    private void handleOrderCreated(OrderResponse order) {
        // Implement order creation handling logic
        log.info("Processing order creation event for order: {}", order.id());
    }

    private void handleOrderUpdated(OrderResponse order) {
        // Implement order update handling logic
        log.info("Processing order update event for order: {}", order.id());
    }

    private void handleOrderDeleted(OrderResponse order) {
        // Implement order deletion handling logic
        log.info("Processing order deletion event for order: {}", order.id());
    }
} 