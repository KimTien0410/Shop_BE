package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.ProductVariantResponse;
import com.fashion.Shop_BE.service.ProductVariantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/variants")
@Tag(name = "ProductVariant", description = "Quản lý biến thể sản phẩm")
public class ProductVariantController {
    private final ProductVariantService productVariantService;
    @GetMapping("/{variantId}")
    @Operation(summary = "Lấy biến thể sản phẩm theo id")
    public ApiResponse<ProductVariantResponse> getProductVariantById(@PathVariable Long variantId) {
        return ApiResponse.<ProductVariantResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy biến thể sản phẩm thành công!")
                .data(productVariantService.getById(variantId))
                .build();
    }

    @GetMapping("/products/{productId}")
    @Operation(summary = "Lấy biến thể sản phẩm theo id sản phẩm")
    public ApiResponse<ProductVariantResponse> getByProductAndColorAndSize(@PathVariable Long productId,@RequestParam String color, @RequestParam String size) {
//        System.out.println(productVariantService.getByProductIdAndColorAndSize(productId, color, size));
        log.info("gey "+productVariantService.getByProductIdAndColorAndSize(productId, color, size));
        return ApiResponse.<ProductVariantResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy biến thể sản phẩm thành công!")
                .data(productVariantService.getByProductIdAndColorAndSize(productId, color, size))
                .build();
    }
    @GetMapping("/colors/{productId}")
    @Operation(summary = "Lấy tất cả màu sắc của sản phẩm")
    public ApiResponse<Set<String>> getColors(@PathVariable Long productId) {
        return ApiResponse.<Set<String>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy tất cả màu sắc của sản phẩm thành công!")
                .data(productVariantService.getColorsByProductId(productId))
                .build();
    }

    @GetMapping("/sizes/{productId}")
    @Operation(summary = "Lấy tất cả size của sản phẩm")
    public ApiResponse<Set<String>> getSizes(@PathVariable Long productId) {
        return ApiResponse.<Set<String>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy tất cả size của sản phẩm thành công!")
                .data(productVariantService.getSizeByProductId(productId))
                .build();
    }
}
