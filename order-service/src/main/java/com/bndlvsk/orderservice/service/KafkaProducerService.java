package com.bndlvsk.orderservice.service;

import com.bndlvsk.orderservice.dto.event.OrderEvent;

public interface KafkaProducerService {
    void sendOrderEvent(OrderEvent orderEvent);
} 