package com.bndlvsk.orderservice.service;

import com.bndlvsk.orderservice.dto.event.OrderEvent;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendOrderEvent(String eventType, OrderResponse order) {
        try {
            OrderEvent event = OrderEvent.builder()
                    .eventType(eventType)
                    .order(order)
                    .timestamp(LocalDateTime.now())
                    .build();

            kafkaTemplate.send("order-events", eventType, event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("Order event sent successfully: {} for order: {}", eventType, order.id());
                        } else {
                            log.error("Failed to send order event: {} for order: {}", eventType, order.id(), ex);
                        }
                    });
        } catch (Exception e) {
            log.error("Error sending order event: {} for order: {}", eventType, order.id(), e);
        }
    }
} 