package com.fashion.Shop_BE.service;

import com.fashion.Shop_BE.dto.request.RequestCategory;
import com.fashion.Shop_BE.dto.response.CategoryResponse;
import com.fashion.Shop_BE.entity.Category;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CategoryService {
    public CategoryResponse convertToDTO(Category category);
    public CategoryResponse addCategory(RequestCategory request);
    public CategoryResponse updateCategory(Long categoryId,RequestCategory request);
    public List<CategoryResponse> getAllCategories();
    public CategoryResponse getCategoryById(Long categoryId);
    public CategoryResponse getCategoryByCode(String categoryCode);
    public void deleteCategory(Long categoryId);
    public void restoreCategory(Long categoryId);
    public Page<CategoryResponse> getAllCategories(String search, int page, int size, String sortBy, String sortOrder);
}
