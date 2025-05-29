package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestCategory;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.CategoryResponse;
import com.fashion.Shop_BE.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "API quản lý danh mục")
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping()
    @Operation(summary = "Lấy danh sách danh mục")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.<List<CategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh mục thành công!")
                .data(categoryService.getAllCategories())
                .build();
    }
    @GetMapping("/code/{categoryCode}")
    @Operation(summary = "Lấy danh mục theo mã danh mục")
    public ApiResponse<CategoryResponse> getCategoryByCode(@PathVariable String categoryCode) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh mục thành công!")
                .data(categoryService.getCategoryByCode(categoryCode))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Thêm mới danh mục")
    public ApiResponse<CategoryResponse> addCategory(@Valid @ModelAttribute RequestCategory category) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm mới danh mục thành công!")
                .data(categoryService.addCategory(category))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    @Operation(summary = "Cập nhật danh mục")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable Long categoryId, @Valid @ModelAttribute RequestCategory category) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật danh mục thành công!")
                .data(categoryService.updateCategory(categoryId, category))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    @Operation(summary = "Xoá danh mục")
    public ApiResponse<?> deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.builder().code(HttpStatus.OK.value())
                .message("Xoá danh mục thành công!")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/restore/{categoryId}")
    @Operation(summary = "Khôi phục danh mục")
    public ApiResponse<?> restoreCategory(@PathVariable Long categoryId) {
        categoryService.restoreCategory(categoryId);
        return ApiResponse.builder().code(HttpStatus.OK.value())
                .message("Khôi phục danh mục thành công!")
                .build();
    }


    @GetMapping("/{categoryId}")
    @Operation(summary = "Lấy danh mục theo id")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long categoryId) {
        return ApiResponse.<CategoryResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh mục thành công!")
                .data(categoryService.getCategoryById(categoryId))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/getAll")
    @Operation(summary = "Lấy danh sách danh mục cho admin")
    public ApiResponse<Page<CategoryResponse>> getAllByAdmin(
            @RequestParam(required = false,defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false,defaultValue = "categoryId") String sortBy,
             @RequestParam(required = false,defaultValue = "desc") String sortDir) {
        return ApiResponse.<Page<CategoryResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh mục thành công!")
                .data(categoryService.getAllCategories(search,page, size,sortBy,sortDir))
                .build();
    }


}
