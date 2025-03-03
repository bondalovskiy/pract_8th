package com.bndlvsk.orderservice.service;

import com.bndlvsk.orderservice.dto.request.OrderCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderItemCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderUpdateRequest;
import com.bndlvsk.orderservice.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderCreateRequest createOrderRequest);

    OrderResponse updateOrder(Long orderId, OrderUpdateRequest updateOrderRequest);

    void deleteOrder(Long id);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();

    void addOrderItem(Long orderId, OrderItemCreateRequest orderItemCreateRequest);

    void addOrderItems(Long orderId, List<OrderItemCreateRequest> orderItemCreateRequests);
}
