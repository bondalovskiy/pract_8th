package com.bndlvsk.productservice.service.impl;

import com.bndlvsk.productservice.dto.request.ProductCreateRequest;
import com.bndlvsk.productservice.dto.request.ProductUpdateRequest;
import com.bndlvsk.productservice.dto.response.ProductResponse;
import com.bndlvsk.productservice.exception.DuplicateFoundException;
import com.bndlvsk.productservice.exception.ResourceNotFoundException;
import com.bndlvsk.productservice.mapper.ProductMapper;
import com.bndlvsk.productservice.model.Product;
import com.bndlvsk.productservice.repository.ProductRepository;
import com.bndlvsk.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bndlvsk.productservice.util.ErrorMessage.*;
import static com.bndlvsk.productservice.util.ErrorMessages.NOT_FOUND_MESSAGE;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductCreateRequest createProductRequest) {
        checkUniqueProductName(createProductRequest);

        Product product = productMapper.createRequestToEntity(createProductRequest);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductUpdateRequest updateProductRequest) {
        Product product = findProductByIdOrThrow(id);

        checkUniqueProductName(updateProductRequest, product.getName());

        productMapper.updateProductFromUpdateRequest(updateProductRequest,product);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "Product", id));
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = findProductByIdOrThrow(id);
        return productMapper.toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Override
    public void handleOrderCreated(Long productId, Integer quantity) {
        Product product = findProductByIdOrThrow(productId);
        // Decrease product quantity when order is created
        if (product.getQuantity() < quantity) {
            log.error("Insufficient quantity for product {}: requested {}, available {}", 
                productId, quantity, product.getQuantity());
            // You might want to handle this case differently, e.g., throw an exception
            return;
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        log.info("Updated product {} quantity after order creation: {}", productId, product.getQuantity());
    }

    @Override
    public void handleOrderUpdated(Long productId, Integer quantity) {
        log.info("Order updated for product {}, new quantity: {}", productId, quantity);
    }

    @Override
    public void handleOrderDeleted(Long productId, Integer quantity) {
        Product product = findProductByIdOrThrow(productId);
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
        log.info("Restored quantity for product {} after order deletion: {}", productId, product.getQuantity());
    }

    private boolean uniqueProductCheck(ProductCreateRequest createProductRequest) {
        return productRepository.existsByName(createProductRequest.name());
    }

    private boolean uniqueProductCheck(ProductUpdateRequest updateProductRequest, String existingName) {
        return updateProductRequest.name() != null &&
                !updateProductRequest.name().equals(existingName) &&
                productRepository.existsByName(updateProductRequest.name());
    }

    private void checkUniqueProductName(ProductCreateRequest createProductRequest) {
        if (uniqueProductCheck(createProductRequest)) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, "product", createProductRequest.name()));
        }
    }

    private void checkUniqueProductName(ProductUpdateRequest updateProductRequest, String existingName) {
        if (uniqueProductCheck(updateProductRequest, existingName)) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, "product", updateProductRequest.name()));
        }
    }

    private Product findProductByIdOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "Product", id)));
    }
}
