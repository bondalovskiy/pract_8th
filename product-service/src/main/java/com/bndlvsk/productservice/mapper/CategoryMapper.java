package com.bndlvsk.productservice.mapper;

import com.bndlvsk.productservice.dto.request.CategoryCreateRequest;
import com.bndlvsk.productservice.dto.request.CategoryUpdateRequest;
import com.bndlvsk.productservice.dto.response.CategoryResponse;
import com.bndlvsk.productservice.model.Category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category createRequestToEntity(CategoryCreateRequest categoryCreateRequest);

    void updateCategoryFromUpdateRequest(CategoryUpdateRequest categoryUpdateRequest, @MappingTarget Category category);

    CategoryResponse toResponse(Category category);
}
