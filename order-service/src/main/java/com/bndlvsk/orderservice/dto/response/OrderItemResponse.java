package com.bndlvsk.orderservice.dto.response;

public record OrderItemResponse(
        Long id,
        Long product,
        Long quantity
){}
