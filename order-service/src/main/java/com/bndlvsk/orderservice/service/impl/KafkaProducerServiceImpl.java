package com.bndlvsk.orderservice.service.impl;

import com.bndlvsk.orderservice.dto.event.OrderEvent;
import com.bndlvsk.orderservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.topic.order-events}")
    private String orderEventsTopic;

    @Override
    public void sendOrderEvent(OrderEvent orderEvent) {
        if (orderEvent.getEventId() == null) {
            orderEvent.setEventId(UUID.randomUUID().toString());
        }

        String key = orderEvent.getData().getOrderId().toString();
        
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(
                orderEventsTopic, 
                key, 
                orderEvent
        );

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Unable to send order event to Kafka for orderId: {}, reason: {}", 
                        key, ex.getMessage());
            } else {
                log.info("Order event sent successfully for orderId: {}, metadata: {}", 
                        key, result.getRecordMetadata());
            }
        });
    }
} 