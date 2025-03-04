package com.bndlvsk.orderservice.controller;

import com.bndlvsk.orderservice.dto.request.*;
import com.bndlvsk.orderservice.dto.response.OrderResponse;
import com.bndlvsk.orderservice.dto.response.OrderItemResponse;
import com.bndlvsk.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid OrderCreateRequest orderCreateRequest) {
        return orderService.createOrder(orderCreateRequest);
    }

    @PutMapping("/{orderId}")
    public OrderResponse updateOrder(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderUpdateRequest orderUpdateRequest) {
        return orderService.updateOrder(orderId, orderUpdateRequest);
    }

    @GetMapping("/{orderId}")
    public OrderResponse getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
    }

    @PostMapping("/{orderId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemResponse addOrderItem(
            @PathVariable Long orderId,
            @RequestBody @Valid OrderItemCreateRequest orderItemCreateRequest) {
        return orderService.addOrderItem(orderId, orderItemCreateRequest);
    }

    @PutMapping("/items/{orderItemId}")
    public OrderItemResponse updateOrderItem(
            @PathVariable Long orderItemId,
            @RequestBody @Valid OrderItemUpdateRequest orderItemUpdateRequest) {
        return orderService.updateOrderItem(orderItemId, orderItemUpdateRequest);
    }

    @DeleteMapping("/items/{orderItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderItem(@PathVariable Long orderItemId) {
        orderService.deleteOrderItem(orderItemId);
    }
}
