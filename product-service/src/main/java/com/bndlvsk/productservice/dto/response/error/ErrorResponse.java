package com.bndlvsk.productservice.dto.response.error;

import lombok.Builder;

@Builder
public record ErrorResponse(

        int status,

        String message
) {
}
