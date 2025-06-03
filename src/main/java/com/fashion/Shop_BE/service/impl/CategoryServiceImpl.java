package com.fashion.Shop_BE.service.impl;

import com.fashion.Shop_BE.dto.request.RequestCategory;
import com.fashion.Shop_BE.dto.response.CategoryResponse;
import com.fashion.Shop_BE.entity.Category;
import com.fashion.Shop_BE.entity.Product;
import com.fashion.Shop_BE.exception.AppException;
import com.fashion.Shop_BE.exception.ErrorCode;
import com.fashion.Shop_BE.mapper.CategoryMapper;
import com.fashion.Shop_BE.repository.CategoryRepository;
import com.fashion.Shop_BE.repository.ProductRepository;
import com.fashion.Shop_BE.service.CategoryService;
import com.fashion.Shop_BE.service.CloudinaryService;
import com.fashion.Shop_BE.service.SlugService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final CloudinaryService cloudinaryService;
    private final SlugService slugService;
    private final ProductRepository productRepository;

    @Override
    public CategoryResponse convertToDTO(Category category){
        CategoryResponse categoryResponse = new CategoryResponse(category);
        if(category.getSubCategories() !=null){
            categoryResponse.setChildren(category.getSubCategories().stream()
                    .map(this::convertToDTO).collect(Collectors.toList()));
        }

        return categoryResponse;
    }


    @Override
    public CategoryResponse addCategory(RequestCategory request) {
        Category category=categoryMapper.requestToCategory(request);
        category.setCategoryCode(slugService.generateSlug(request.getCategoryName()));
        MultipartFile categoryThumbnail=request.getCategoryThumbnail();

        if(request.getParentId() !=null){
            Category parentCategory=categoryRepository.findById(request.getParentId())
                    .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParentCategory(parentCategory);
        }

        if(categoryThumbnail!=null && !categoryThumbnail.isEmpty()){
            String url=cloudinaryService.uploadFile(categoryThumbnail);
            category.setCategoryThumbnail(url);
        }

        categoryRepository.save(category);
        return convertToDTO(category);
    }

    @Override
    public CategoryResponse updateCategory(Long categoryId, RequestCategory request) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoryMapper.updateCategory(category,request);
        category.setCategoryCode(slugService.generateSlug(request.getCategoryName()));
        Long categoryParentId=request.getParentId();
        if(categoryParentId != null){
            Category categoryParent=categoryRepository.findById(categoryParentId)
                    .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParentCategory(categoryParent);
        }
        MultipartFile categoryThumbnail=request.getCategoryThumbnail();
        if(categoryThumbnail !=null){
            if(category.getCategoryThumbnail()!=null && !categoryThumbnail.isEmpty()){
                cloudinaryService.deleteOldFile(category.getCategoryThumbnail());
            }
            String url=cloudinaryService.uploadFile(categoryThumbnail);
            category.setCategoryThumbnail(url);
        }
        categoryRepository.save(category);

        return convertToDTO(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categoryList=categoryRepository.findByParentCategoryIsNullAndDeletedAtIsNull();
        return categoryList.stream().map(this::convertToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public CategoryResponse getCategoryById(Long categoryId) {
        Category category=categoryRepository.findByCategoryIdAndDeletedAtIsNull(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return convertToDTO(category);
    }

    @Override
    public CategoryResponse getCategoryByCode(String categoryCode) {
        Category category=categoryRepository.findByCategoryCodeAndDeletedAtIsNull(categoryCode)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        return convertToDTO(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        // Nếu danh mục không có danh mục con và không có sản phẩm, xoá cứng
        if(category.getSubCategories() == null || category.getSubCategories().isEmpty()){
            if(category.getProducts() == null || category.getProducts().isEmpty()) {
                categoryRepository.delete(category);
                return;
            }
        }

        category.setDeletedAt(new Date());

        categoryRepository.save(category);
    }
    @Transactional
    @Override
    public void restoreCategory(Long categoryId) {
        Category category= categoryRepository.findById(categoryId)
                .orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        category.setDeletedAt(null);
        categoryRepository.save(category);
    }

    @Override
    public Page<CategoryResponse> getAllCategories(String search, int page, int size, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Category> categories;
        if (search == null || search.trim().isEmpty()) {
            categories = categoryRepository.findByParentCategoryIsNull(pageable);
        } else {
            categories = categoryRepository.findByParentCategoryIsNull(search, pageable);
        }
        return categories.map(category -> {
            CategoryResponse categoryResponse = convertToDTO(category);
            categoryResponse.setCategoryId(category.getCategoryId());
            return categoryResponse;
        });

    }
}
