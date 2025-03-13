package com.bndlvsk.orderservice.consumer;

import com.bndlvsk.orderservice.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Profile("test-consumer") // Only activate this consumer in the test-consumer profile
public class TestOrderEventConsumer {

    @KafkaListener(topics = "${kafka.topic.order-created}", groupId = "test-order-consumer-group")
    public void handleOrderCreatedEvent(OrderEvent event) {
        log.info("Received order created event: {}", event);
        log.info("Order ID: {}, User ID: {}, Price: {}, Items: {}", 
                event.getOrderId(), event.getUserId(), event.getPrice(), event.getItems().size());
    }

    @KafkaListener(topics = "${kafka.topic.order-updated}", groupId = "test-order-consumer-group")
    public void handleOrderUpdatedEvent(OrderEvent event) {
        log.info("Received order updated event: {}", event);
        log.info("Order ID: {}, User ID: {}, Price: {}, Items: {}", 
                event.getOrderId(), event.getUserId(), event.getPrice(), event.getItems().size());
    }

    @KafkaListener(topics = "${kafka.topic.order-deleted}", groupId = "test-order-consumer-group")
    public void handleOrderDeletedEvent(OrderEvent event) {
        log.info("Received order deleted event: {}", event);
        log.info("Order ID: {}", event.getOrderId());
    }
} 