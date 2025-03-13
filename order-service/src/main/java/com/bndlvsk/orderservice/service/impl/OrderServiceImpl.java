package com.bndlvsk.orderservice.service.impl;

import com.bndlvsk.orderservice.client.ProductClient;
import com.bndlvsk.orderservice.client.UserClient;
import com.bndlvsk.orderservice.dto.external.ProductDto;
import com.bndlvsk.orderservice.dto.request.*;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import com.bndlvsk.orderservice.dto.response.OrderItemResponse;
import com.bndlvsk.orderservice.exception.ResourceNotFoundException;
import com.bndlvsk.orderservice.mapper.OrderMapper;
import com.bndlvsk.orderservice.mapper.OrderItemMapper;
import com.bndlvsk.orderservice.model.Order;
import com.bndlvsk.orderservice.model.OrderItem;
import com.bndlvsk.orderservice.repository.OrderRepository;
import com.bndlvsk.orderservice.repository.OrderItemRepository;
import com.bndlvsk.orderservice.service.OrderService;
import com.bndlvsk.orderservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.bndlvsk.orderservice.util.ErrorMessages.NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public OrderResponse createOrder(OrderCreateRequest request) {
        //userClient.checkUserExists(request.userId());

        BigDecimal totalPrice = BigDecimal.ZERO;
        Order order = orderMapper.createRequestToEntity(request);

        for (OrderItemCreateRequest itemRequest : request.items()) {
            OrderItem orderItem = orderItemMapper.createRequestToEntity(itemRequest);
            orderItem.setOrder(order);
            order.addOrderItem(orderItem);
        }
        for (OrderItemCreateRequest item : request.items()) {
            ProductDto product = productClient.getProductById(item.productId());
            BigDecimal itemPrice = product.price().multiply(BigDecimal.valueOf(item.quantity()));
            totalPrice = totalPrice.add(itemPrice);
        }

        order.setPrice(totalPrice);
        Order savedOrder = orderRepository.save(order);
        
        // Send order created event
        kafkaProducerService.sendOrderEvent("ORDER_CREATED", orderMapper.toResponse(savedOrder));
        
        return orderMapper.toResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request) {
        Order order = getOrderOrThrow(orderId);
        orderMapper.updateOrderFromUpdateRequest(request, order);
        Order updatedOrder = orderRepository.save(order);
        
        // Send order updated event
        kafkaProducerService.sendOrderEvent("ORDER_UPDATED", orderMapper.toResponse(updatedOrder));
        
        return orderMapper.toResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "Order", orderId));
        }
        
        // Get order details before deletion for the event
        Order order = getOrderOrThrow(orderId);
        OrderResponse orderResponse = orderMapper.toResponse(order);
        
        orderRepository.deleteById(orderId);
        
        // Send order deleted event
        kafkaProducerService.sendOrderEvent("ORDER_DELETED", orderResponse);
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = getOrderOrThrow(orderId);
        return orderMapper.toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public OrderItemResponse addOrderItem(Long orderId, OrderItemCreateRequest request) {
        Order order = getOrderOrThrow(orderId);

        //productClient.checkProductExists(request.productId());

        OrderItem orderItem = orderItemMapper.createRequestToEntity(request);
        orderItem.setOrder(order);
        order.getOrderItems().add(orderItem);

        orderRepository.save(order);
        return orderItemMapper.toResponse(orderItem);
    }

    @Override
    public OrderItemResponse updateOrderItem(Long orderItemId, OrderItemUpdateRequest request) {
        OrderItem orderItem = getOrderItemOrThrow(orderItemId);
        orderItemMapper.updateOrderItemFromUpdateRequest(request, orderItem);
        return orderItemMapper.toResponse(orderItemRepository.save(orderItem));
    }

    @Override
    public void deleteOrderItem(Long orderItemId) {
        if (!orderItemRepository.existsById(orderItemId)) {
            throw new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "OrderItem", orderItemId));
        }
        orderItemRepository.deleteById(orderItemId);
    }

    private Order getOrderOrThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(NOT_FOUND_MESSAGE, "Order", orderId))
                );
    }

    private OrderItem getOrderItemOrThrow(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format(NOT_FOUND_MESSAGE, "OrderItem", orderItemId))
                );
    }
}
