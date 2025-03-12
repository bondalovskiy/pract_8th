package com.bndlvsk.orderservice.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long id,
        Long userId,
        String address,
        BigDecimal price,
        List<OrderItemResponse> items
) {}
