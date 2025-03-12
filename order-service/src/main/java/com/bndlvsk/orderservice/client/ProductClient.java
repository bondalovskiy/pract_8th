package com.bndlvsk.orderservice.client;

import com.bndlvsk.orderservice.dto.external.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    @GetMapping("/api/v1/products/{id}")
    ProductDto getProductById(@PathVariable Long id);
}
