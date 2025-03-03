package com.bndlvsk.orderservice.dto.response;

import java.math.BigDecimal;

public record OrderResponse(
        Long id,
        Long userId,
        String address,
        BigDecimal price
){}
