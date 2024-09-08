package com.icesi.ecommerce.service.interfaces;

import com.icesi.ecommerce.dto.CategoryRequest;
import com.icesi.ecommerce.dto.CategoryResponse;

public interface ICategoryService {
    CategoryResponse createCategory(CategoryRequest categoryRequest);
    CategoryResponse getCategoryById(Long id);
    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);
    void deleteCategory(Long id);
}