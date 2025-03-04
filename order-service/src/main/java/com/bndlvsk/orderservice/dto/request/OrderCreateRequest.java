package com.bndlvsk.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreateRequest(
        @NotNull(message = "{field.required}")
        Long userId,
        @NotBlank(message = "{field.required}")
        String address,
        @NotNull(message = "{field.null}")
        List<OrderItemCreateRequest> items
) {}
