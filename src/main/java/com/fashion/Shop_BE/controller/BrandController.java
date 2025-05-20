package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestBrand;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.BrandResponse;
import com.fashion.Shop_BE.enums.EntityStatus;
import com.fashion.Shop_BE.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Tag(name="Brand",description = "Quản lý thương hiệu")
public class BrandController {
    private final BrandService brandService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    @Operation(summary = "Thêm thương hiệu")
    public ApiResponse<BrandResponse> addBrand(@Valid @ModelAttribute RequestBrand requestBrand) {
        return ApiResponse.<BrandResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm thương hiệu mới thành công!")
                .data(brandService.addBrand(requestBrand))
                .build();
    }

    @GetMapping()
    @Operation(summary = "Lấy danh sách thương hiệu!")
    public ApiResponse<Page<BrandResponse>> getAllBrands(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "brandId") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        Page<BrandResponse> brandResponses=brandService.getAllBrandActive(search,page,size,sortBy,sortDirection);
        return ApiResponse.<Page<BrandResponse>>builder()
                .data(brandResponses)
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách thương hiệu thành công!")
                .build();
    }
    @GetMapping("/code/{brandCode}")
    @Operation(summary = "Lấy thương hiệu theo mã")
    public ApiResponse<BrandResponse> getBrandByCode(@PathVariable String brandCode) {
        return ApiResponse.<BrandResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin thương hiệu thành công!")
                .data(brandService.getByBrandCode(brandCode))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{brandId}")
    @Operation(summary = "Cập nhật thương hiệu")
    public ApiResponse<BrandResponse> updateBrand(@PathVariable Long brandId,@Valid @ModelAttribute RequestBrand requestBrand) {
        return ApiResponse.<BrandResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật thương hiệu thành công!")
                .data(brandService.updateBrand(brandId, requestBrand))
                .build();
    }

    @GetMapping("/{brandId}")
    @Operation(summary = "Lấy thương hiệu theo id")
    public ApiResponse<BrandResponse> getBrand(@PathVariable Long brandId) {
        return ApiResponse.<BrandResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin thương hiệu thành công!")
                .data(brandService.getBrand(brandId))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{brandId}")
    @Operation(summary = "Xoá thương hiệu")
    public ApiResponse<?> deleteBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Xoá thương hiệu thành công!")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{brandId}")
    @Operation(summary = "Khôi phục thương hiệu")
    public ApiResponse<?> restoreBrand(@PathVariable Long brandId) {
        brandService.restoreBrand(brandId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Xoá thương hiệu thành công!")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    @Operation(summary = "Lấy tất cả thương hiệu!")
    public ApiResponse<Page<BrandResponse>> getAllBrandsAdmin(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "brandId") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        Page<BrandResponse> brandResponses=brandService.getAllBrandByAdmin(search,page,size,sortBy,sortDirection);
        return ApiResponse.<Page<BrandResponse>>builder()
                .data(brandResponses)
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách thương hiệu thành công!")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/filter")
    @Operation(summary = "Lấy tất cả thương hiệu!")
    public ApiResponse<Page<BrandResponse>> getAllBrandsAdmin(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "ALL") EntityStatus status,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "brandId") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        Page<BrandResponse> brandResponses=brandService.getAllBrandByAdmin(search,page,size,sortBy,sortDirection);
        return ApiResponse.<Page<BrandResponse>>builder()
                .data(brandResponses)
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách thương hiệu thành công!")
                .build();
    }


}
