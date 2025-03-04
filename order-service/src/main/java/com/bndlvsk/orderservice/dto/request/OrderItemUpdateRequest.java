package com.bndlvsk.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemUpdateRequest(
        @NotNull(message = "{field.required}")
        @Positive(message = "{field.positive}")
        Long quantity
) {}
