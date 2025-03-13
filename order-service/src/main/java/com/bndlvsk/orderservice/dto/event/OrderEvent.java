package com.bndlvsk.orderservice.dto.event;

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
@NoArgsConstructor
@AllArgsConstructor
public class OrderEvent {
    private String eventId;
    private String eventType;
    private LocalDateTime timestamp;
    private OrderEventData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderEventData {
        private Long orderId;
        private Long userId;
        private String address;
        private BigDecimal price;
        private List<OrderItemResponse> items;
    }
} 