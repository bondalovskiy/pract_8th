package com.bndlvsk.productservice.service;

import com.bndlvsk.productservice.dto.request.ProductCreateRequest;
import com.bndlvsk.productservice.dto.request.ProductUpdateRequest;
import com.bndlvsk.productservice.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductCreateRequest createProductRequest);

    ProductResponse updateProduct(Long id, ProductUpdateRequest productUpdateRequest);

    void deleteProduct(Long id);

    ProductResponse getProductById(Long id);

    List<ProductResponse> getAllProducts();

    // Order event handling methods
    void handleOrderCreated(Long productId, Integer quantity);
    void handleOrderUpdated(Long productId, Integer quantity);
    void handleOrderDeleted(Long productId, Integer quantity);

}
