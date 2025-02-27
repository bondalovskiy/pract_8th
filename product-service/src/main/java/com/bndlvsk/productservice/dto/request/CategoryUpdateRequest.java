package com.bndlvsk.productservice.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(
        @NotBlank(message = "{field.required}")
        String name
){}
