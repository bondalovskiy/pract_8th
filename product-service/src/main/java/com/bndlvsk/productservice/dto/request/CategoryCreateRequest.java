package com.bndlvsk.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank(message = "{field.required}")
        String name
){}
