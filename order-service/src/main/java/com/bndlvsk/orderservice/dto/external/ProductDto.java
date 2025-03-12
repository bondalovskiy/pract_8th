package com.bndlvsk.orderservice.dto.external;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Long categoryId
)
{}
