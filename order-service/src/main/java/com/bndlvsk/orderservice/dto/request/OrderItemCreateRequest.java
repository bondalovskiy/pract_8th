package com.bndlvsk.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemCreateRequest(
        @NotNull(message = "{field.required}")
        Long productId,

        @NotNull(message = "{field.required}")
        @Positive(message = "{field.positive}")
        Long quantity
) {}
