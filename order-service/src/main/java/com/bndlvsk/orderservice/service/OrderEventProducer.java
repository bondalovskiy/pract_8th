package com.bndlvsk.orderservice.service;

import com.bndlvsk.orderservice.model.Order;

public interface OrderEventProducer {
    void publishOrderCreated(Order order);
    void publishOrderUpdated(Order order);
    void publishOrderDeleted(Long orderId);
} 