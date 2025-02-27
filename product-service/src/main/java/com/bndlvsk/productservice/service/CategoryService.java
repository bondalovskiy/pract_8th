package com.bndlvsk.productservice.service;

import com.bndlvsk.productservice.dto.request.CategoryCreateRequest;
import com.bndlvsk.productservice.dto.request.CategoryUpdateRequest;
import com.bndlvsk.productservice.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryCreateRequest createCategoryRequest);

    CategoryResponse updateCategory(Long id, CategoryUpdateRequest categoryUpdateRequest);

    void deleteCategory(Long id);

    CategoryResponse getCategoryById(Long id);

    List<CategoryResponse> getAllCategories();

}
