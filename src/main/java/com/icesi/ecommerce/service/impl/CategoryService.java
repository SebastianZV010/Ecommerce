package com.icesi.ecommerce.service;

import com.icesi.ecommerce.dto.CategoryRequest;
import com.icesi.ecommerce.dto.CategoryResponse;
import com.icesi.ecommerce.entity.CategoryEntity;
import com.icesi.ecommerce.repository.ICategoryRepository;
import com.icesi.ecommerce.service.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        CategoryEntity category = new CategoryEntity();
        category.setName(categoryRequest.name());
        category.setDescription(categoryRequest.description());
        CategoryEntity savedCategory = categoryRepository.save(category);
        return new CategoryResponse(savedCategory.getId(), savedCategory.getName(), savedCategory.getDescription());
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new CategoryResponse(category.getId(), category.getName(), category.getDescription());
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryRequest.name());
        category.setDescription(categoryRequest.description());
        CategoryEntity updatedCategory = categoryRepository.save(category);
        return new CategoryResponse(updatedCategory.getId(), updatedCategory.getName(), updatedCategory.getDescription());
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
