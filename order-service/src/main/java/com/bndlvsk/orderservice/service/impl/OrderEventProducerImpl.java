package com.bndlvsk.orderservice.service.impl;

import com.bndlvsk.orderservice.event.OrderEvent;
import com.bndlvsk.orderservice.model.Order;
import com.bndlvsk.orderservice.model.OrderItem;
import com.bndlvsk.orderservice.service.OrderEventProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducerImpl implements OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.order-created:pizza-order-created}")
    private String orderCreatedTopic;

    @Value("${kafka.topic.order-updated:pizza-order-updated}")
    private String orderUpdatedTopic;

    @Value("${kafka.topic.order-deleted:pizza-order-deleted}")
    private String orderDeletedTopic;

    @Override
    public void publishOrderCreated(Order order) {
        OrderEvent event = buildOrderEvent(order, OrderEvent.EventType.CREATED);
        sendOrderEvent(orderCreatedTopic, String.valueOf(order.getId()), event);
        log.info("Order created event published for order ID: {}", order.getId());
    }

    @Override
    public void publishOrderUpdated(Order order) {
        OrderEvent event = buildOrderEvent(order, OrderEvent.EventType.UPDATED);
        sendOrderEvent(orderUpdatedTopic, String.valueOf(order.getId()), event);
        log.info("Order updated event published for order ID: {}", order.getId());
    }

    @Override
    public void publishOrderDeleted(Long orderId) {
        OrderEvent event = OrderEvent.builder()
                .orderId(orderId)
                .timestamp(LocalDateTime.now())
                .eventType(OrderEvent.EventType.DELETED)
                .build();
        
        sendOrderEvent(orderDeletedTopic, String.valueOf(orderId), event);
        log.info("Order deleted event published for order ID: {}", orderId);
    }

    private OrderEvent buildOrderEvent(Order order, OrderEvent.EventType eventType) {
        return OrderEvent.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .address(order.getAddress())
                .price(order.getPrice())
                .items(order.getOrderItems().stream()
                        .map(this::mapToOrderItemEvent)
                        .collect(Collectors.toList()))
                .timestamp(LocalDateTime.now())
                .eventType(eventType)
                .build();
    }

    private OrderEvent.OrderItemEvent mapToOrderItemEvent(OrderItem orderItem) {
        return OrderEvent.OrderItemEvent.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .build();
    }

    private void sendOrderEvent(String topic, String key, OrderEvent event) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, event);
        
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Message sent successfully to topic {} with key {}, partition {}", 
                        topic, key, result.getRecordMetadata().partition());
            } else {
                log.error("Failed to send message to topic {} with key {}", topic, key, ex);
            }
        });
    }
} 