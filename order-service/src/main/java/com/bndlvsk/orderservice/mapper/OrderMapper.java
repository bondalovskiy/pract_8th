package com.bndlvsk.orderservice.mapper;

import com.bndlvsk.orderservice.dto.request.OrderCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderUpdateRequest;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import com.bndlvsk.orderservice.model.Order;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    //@Mapping(target = "orderItems", source = "items")
    Order createRequestToEntity(OrderCreateRequest orderCreateRequest);

    void updateOrderFromUpdateRequest(OrderUpdateRequest orderUpdateRequest, @MappingTarget Order order);

    @Mapping(target = "items", source = "orderItems")
    OrderResponse toResponse(Order order);
}
