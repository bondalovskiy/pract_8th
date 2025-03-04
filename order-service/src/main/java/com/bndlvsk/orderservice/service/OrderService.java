package com.bndlvsk.orderservice.service;

import com.bndlvsk.orderservice.dto.request.OrderCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderUpdateRequest;
import com.bndlvsk.orderservice.dto.request.OrderItemCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderItemUpdateRequest;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import com.bndlvsk.orderservice.dto.response.OrderItemResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderCreateRequest request);

    OrderResponse updateOrder(Long orderId, OrderUpdateRequest request);

    void deleteOrder(Long orderId);

    OrderResponse getOrderById(Long orderId);

    List<OrderResponse> getAllOrders();

    OrderItemResponse addOrderItem(Long orderId, OrderItemCreateRequest request);

    OrderItemResponse updateOrderItem(Long orderItemId, OrderItemUpdateRequest request);

    void deleteOrderItem(Long orderItemId);
}
