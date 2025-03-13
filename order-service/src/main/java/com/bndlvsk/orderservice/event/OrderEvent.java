package com.bndlvsk.orderservice.event;

import com.bndlvsk.orderservice.dto.response.OrderItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private Long orderId;
    private Long userId;
    private String address;
    private BigDecimal price;
    private List<OrderItemEvent> items;
    private LocalDateTime timestamp;
    private EventType eventType;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemEvent {
        private Long id;
        private Long productId;
        private Integer quantity;
    }

    public enum EventType {
        CREATED,
        UPDATED,
        DELETED
    }
} 