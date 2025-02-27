package com.bndlvsk.productservice.service.impl;

import com.bndlvsk.productservice.dto.request.CategoryCreateRequest;
import com.bndlvsk.productservice.dto.request.CategoryUpdateRequest;
import com.bndlvsk.productservice.dto.response.CategoryResponse;
import com.bndlvsk.productservice.exception.DuplicateFoundException;
import com.bndlvsk.productservice.exception.ResourceNotFoundException;
import com.bndlvsk.productservice.mapper.CategoryMapper;
import com.bndlvsk.productservice.model.Category;
import com.bndlvsk.productservice.repository.CategoryRepository;
import com.bndlvsk.productservice.service.CategoryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bndlvsk.productservice.util.ErrorMessage.*;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryCreateRequest createCategoryRequest) {
        checkUniqueCategoryName(createCategoryRequest);

        Category category = categoryMapper.createRequestToEntity(createCategoryRequest);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryUpdateRequest updateCategoryRequest) {
        Category category = findCategoryByIdOrThrow(id);

        checkUniqueCategoryName(updateCategoryRequest, category.getName());

        categoryMapper.updateCategoryFromUpdateRequest(updateCategoryRequest, category);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = findCategoryByIdOrThrow(id);
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
       return categoryRepository.findAll().stream()
               .map(categoryMapper::toResponse)
               .toList();
    }

    private boolean uniqueCategoryCheck(CategoryCreateRequest createCategoryRequest) {
        return categoryRepository.existsByName(createCategoryRequest.name());
    }

    private boolean uniqueCategoryCheck(CategoryUpdateRequest updateCategoryRequest, String existingName) {
        return updateCategoryRequest.name() != null &&
                !updateCategoryRequest.name().equals(existingName) &&
                categoryRepository.existsByName(updateCategoryRequest.name());
    }

    private void checkUniqueCategoryName(CategoryCreateRequest createCategoryRequest) {
        if (uniqueCategoryCheck(createCategoryRequest)) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, "category", createCategoryRequest.name()));
        }
    }

    private void checkUniqueCategoryName(CategoryUpdateRequest updateCategoryRequest, String existingName) {
        if (uniqueCategoryCheck(updateCategoryRequest, existingName)) {
            throw new DuplicateFoundException(String.format(ALREADY_USED_MESSAGE, "category", updateCategoryRequest.name()));
        }
    }

    private Category findCategoryByIdOrThrow(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(String.format(NOT_FOUND_MESSAGE, "Category", id))
                );
    }
}
