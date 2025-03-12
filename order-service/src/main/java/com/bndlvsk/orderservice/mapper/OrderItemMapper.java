package com.bndlvsk.orderservice.mapper;

import com.bndlvsk.orderservice.dto.request.OrderItemCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderItemUpdateRequest;
import com.bndlvsk.orderservice.dto.response.OrderItemResponse;
import com.bndlvsk.orderservice.model.OrderItem;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderItemMapper {

    OrderItem createRequestToEntity(OrderItemCreateRequest orderItemCreateRequest);

    void updateOrderItemFromUpdateRequest(OrderItemUpdateRequest updateRequest, @MappingTarget OrderItem orderItem);

    OrderItemResponse toResponse(OrderItem orderItem);
}
