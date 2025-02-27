package com.bndlvsk.productservice.mapper;

import com.bndlvsk.productservice.dto.request.ProductCreateRequest;
import com.bndlvsk.productservice.dto.request.ProductUpdateRequest;
import com.bndlvsk.productservice.dto.response.ProductResponse;
import com.bndlvsk.productservice.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "orderItems", ignore = true)
    Product createRequestToEntity(ProductCreateRequest productCreateRequest);

    void updateProductFromUpdateRequest(ProductUpdateRequest productUpdateRequest, @MappingTarget Product product);

    @Mapping(source = "product.category.id", target = "categoryId")
    ProductResponse toResponse(Product product);
}
