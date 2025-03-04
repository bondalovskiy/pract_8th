package com.bndlvsk.orderservice.mapper;

import com.bndlvsk.orderservice.dto.request.OrderCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderUpdateRequest;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import com.bndlvsk.orderservice.model.Order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
    //@Mapping(target = "user", ignore = true)
    Order createRequestToEntity(OrderCreateRequest orderCreateRequest);

    void updateOrderFromUpdateRequest(OrderUpdateRequest orderUpdateRequest, @MappingTarget Order order);

    //@Mapping(source = "order.user.id", target = "userId")
    OrderResponse toResponse(Order order);
}
