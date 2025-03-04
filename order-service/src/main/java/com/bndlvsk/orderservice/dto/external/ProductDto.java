package com.bndlvsk.orderservice.dto.external;

import lombok.Data;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        Long categoryId
)
{}
