package com.bndlvsk.orderservice.mapper;

import com.bndlvsk.orderservice.dto.request.OrderItemCreateRequest;
import com.bndlvsk.orderservice.dto.response.OrderItemResponse;
import com.bndlvsk.orderservice.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper{
    OrderItem createRequestToEntity(OrderItemCreateRequest orderItemCreateRequest);

    OrderItemResponse toResponse(OrderItem orderItem);
}

