package com.bndlvsk.orderservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderUpdateRequest(
        @NotBlank(message = "{field.required}")
        String address,

        @NotNull(message = "{field.required}")
        @Positive(message = "{field.positive}")
        BigDecimal price
) {}
