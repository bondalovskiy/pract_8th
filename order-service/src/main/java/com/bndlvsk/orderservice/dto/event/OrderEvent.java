package com.bndlvsk.orderservice.dto.event;

import com.bndlvsk.orderservice.dto.response.OrderResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderEvent {
    private String eventType;
    private OrderResponse order;
    private LocalDateTime timestamp;
} 