package com.bndlvsk.orderservice.controller;

import com.bndlvsk.orderservice.client.ProductClient;
import com.bndlvsk.orderservice.client.UserClient;
import com.bndlvsk.orderservice.dto.external.ProductDto;
import com.bndlvsk.orderservice.dto.external.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final UserClient userClient;
    private final ProductClient productClient;

    @GetMapping("/user/{id}")
    public UserDto testUser(@PathVariable Long id) {
        return userClient.getUserById(id);
    }

    @GetMapping("/product/{id}")
    public ProductDto testProduct(@PathVariable Long id) {
        return productClient.getProductById(id);
    }
}

