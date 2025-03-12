package com.bndlvsk.orderservice.dto.response.error;

import lombok.Builder;

@Builder
public record ErrorResponse(

        int status,

        String message
) {
}
