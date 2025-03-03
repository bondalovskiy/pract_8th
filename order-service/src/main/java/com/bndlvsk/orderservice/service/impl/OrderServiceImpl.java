package com.bndlvsk.orderservice.service.impl;

import com.bndlvsk.orderservice.dto.request.OrderCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderItemCreateRequest;
import com.bndlvsk.orderservice.dto.request.OrderUpdateRequest;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import com.bndlvsk.orderservice.exception.ResourceNotFoundException;
import com.bndlvsk.orderservice.mapper.OrderItemMapper;
import com.bndlvsk.orderservice.mapper.OrderMapper;
import com.bndlvsk.orderservice.model.Order;
import com.bndlvsk.orderservice.model.OrderItem;
import com.bndlvsk.orderservice.repository.OrderItemRepository;
import com.bndlvsk.orderservice.repository.OrderRepository;
import com.bndlvsk.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.bndlvsk.orderservice.util.ErrorMessages.NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;

    @Override
    public OrderResponse createOrder(OrderCreateRequest createOrderRequest) {
        Order order = orderMapper.createRequestToEntity(createOrderRequest);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderUpdateRequest orderUpdateRequest) {
        Order order = findOrderByIdOrThrow(id);
        orderMapper.updateOrderFromUpdateRequest(orderUpdateRequest,order);
        return orderMapper.toResponse(orderRepository.save(order));
    }

    @Override
    public void addOrderItem(Long orderId, OrderItemCreateRequest orderItemCreateRequest) {
        Order order = findOrderByIdOrThrow(orderId);
        OrderItem orderItem = orderItemMapper.createRequestToEntity(orderItemCreateRequest);
        order.addOrderItem(orderItem);
        orderRepository.save(order);
    }

    @Override
    public void addOrderItems(Long orderId, List<OrderItemCreateRequest> orderItemCreateRequests) {
        Order order = findOrderByIdOrThrow(orderId);
        List<OrderItem> items = orderItemCreateRequests.stream()
                .map(request -> buildOrderItem(order, request))
                .collect(Collectors.toList());
        order.getOrderItems().addAll(items);
        orderRepository.save(order);
        items.stream()
                .map(orderItemMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = findOrderByIdOrThrow(id);
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    private OrderItem buildOrderItem(Order order, OrderItemCreateRequest request) {
        OrderItem orderItem = orderItemMapper.createRequestToEntity(request);
        orderItem.setOrder(order);
        return orderItem;
    }

    private Order findOrderByIdOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "Order", id))
                );
    }
}
