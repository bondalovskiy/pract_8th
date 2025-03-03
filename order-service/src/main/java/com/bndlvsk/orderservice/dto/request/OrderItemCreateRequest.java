package com.bndlvsk.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemCreateRequest(
        @NotNull(message = "{field.null}")
        Long productId,
        @NotNull(message = "{field.null}")
        @Positive(message = "{field.positive}")
        Long quantity
){}
