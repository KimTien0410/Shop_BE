package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.*;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.ProductItemResponse;
import com.fashion.Shop_BE.dto.response.ProductResponse;
import com.fashion.Shop_BE.enums.Size;
import com.fashion.Shop_BE.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name="Product", description = "Quản lý sản phẩm")
public class ProductController {
    private final ProductService productService;
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping
//    @Operation(summary = "Thêm sản phẩm")
//    public ApiResponse<ProductResponse> addProduct(@RequestPart("product") @Valid RequestProduct product,
//                                                   @RequestPart(value = "productVariants") @Valid List<RequestProductVariant> productVariants,
//                                                   @RequestPart(value="images",required = false) @Valid List<MultipartFile> images) {
//        System.out.println("Product: " + product);
//        System.out.println("Product Variants: " + productVariants);
//        System.out.println("Images: " + (images != null ? images.size() : "No images"));
//        return ApiResponse.<ProductResponse>builder()
//                .data(productService.addProduct(product,productVariants,images))
//                .code(HttpStatus.CREATED.value())
//                .message("Thêm sản phẩm mới thành công!")
//                .build();
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Thêm sản phẩm")
    public ApiResponse<ProductResponse> addProduct(@ModelAttribute RequestProduct product) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.addProduct(product,product.getProductVariants(),product.getImages()))
                .code(HttpStatus.CREATED.value())
                .message("Thêm sản phẩm mới thành công!")
                .build();
    }



//    @PreAuthorize("hasRole('ADMIN')")
//    @PutMapping("/{productId}")
//    @Operation(summary = "Cập nhật sản phẩm")
//    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long productId,
//                                                    @RequestPart("product") @Valid RequestProduct product,
//                                                   @RequestPart(value = "productVariants") @Valid List<RequestUpdateProductVariant> productVariants,
//                                                   @RequestPart(value="images",required = false) @Valid List<MultipartFile> images) {
//        return ApiResponse.<ProductResponse>builder()
//                .data(productService.updateProduct(productId,product,productVariants,images))
//                .code(HttpStatus.OK.value())
//                .message("Cập nhật sản phẩm thành công!")
//                .build();
//    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    @Operation(summary = "Cập nhật sản phẩm")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable Long productId,
                                                      @ModelAttribute @Valid RequestUpdateProduct product) {
        return ApiResponse.<ProductResponse>builder()
                .data(productService.updateProduct(productId,product,product.getProductVariants(),product.getImages()))
                .code(HttpStatus.OK.value())
                .message("Cập nhật sản phẩm thành công!")
                .build();
    }


    @GetMapping("/slug/{productSlug}")
    @Operation(summary = "Lấy sản phẩm theo slug")
    public ApiResponse<ProductResponse> getProductByProductSlug(@PathVariable("productSlug") String productSlug) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin chi tiết sản phẩm thành công!")
                .data(productService.getProductBySlug(productSlug))
                .build();
    }
    @GetMapping("/{productId}")
    @Operation(summary = "Lấy sản phẩm theo id")
    public ApiResponse<ProductResponse> getProductById(@PathVariable("productId") Long productId) {
        return ApiResponse.<ProductResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin chi tiết sản phẩm thành công!")
                .data(productService.getProduct(productId))
                .build();
    }
//    @GetMapping()
//    @Operation(summary = "Lấy tất cả sản phẩm thành công!")
//    public ApiResponse<Page<ProductItemResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
//        return ApiResponse.<Page<ProductItemResponse>>builder()
//                .code(HttpStatus.OK.value())
//                .message("Lấy danh sách sản phẩm thành công!")
//                .data(productService.getAllProducts(page, size))
//                .build();
//    }



    @GetMapping("/filter")
    @Operation(summary = "Lấy tất cả sản phẩm")
    public ApiResponse<Page<ProductItemResponse>> filterProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Set<String> brandCode,
            @RequestParam(required = false) Set<String> color,
            @RequestParam(required = false) Set<Size> size,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean isNewArrival,
            @RequestParam(required = false) Boolean isBestSeller,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int sizePerPage,
            @RequestParam(defaultValue = "productId") String orderBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        ProductFilterRequest productFilterRequest = ProductFilterRequest.builder()
                .search(search)
                .categoryId(categoryId)
                .brandCode(brandCode)
                .color(color)
                .size(size)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .isNewArrival(isNewArrival)
                .isBestSeller(isBestSeller)
                .build();
        Page<ProductItemResponse> productResponses = productService.filterProduct(productFilterRequest,page, sizePerPage,orderBy,sortDirection);
        return ApiResponse.<Page<ProductItemResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm thành công")
                .data(productResponses)
                .build();
    }

    @PostMapping("/search")
    @Operation(summary = "Tìm kiếm sản phẩm")
    public ApiResponse<Page<ProductItemResponse>> searchProducts(@RequestBody ProductFilterRequest productFilterRequest,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int sizePerPage,
            @RequestParam(defaultValue = "productId") String orderBy,
            @RequestParam(defaultValue = "desc") String sortDirection)  {
        Page<ProductItemResponse> productResponses = productService.filterProduct(productFilterRequest,page, sizePerPage,orderBy,sortDirection);
        return ApiResponse.<Page<ProductItemResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm thành công")
                .data(productResponses)
                .build();
    }


    @GetMapping("/by-variant/{variantId}")
    @Operation(summary = "Lấy sản phẩm theo variantId")
    public ApiResponse<ProductItemResponse> getProductByVariantId(@PathVariable("variantId") Long variantId) {
        return ApiResponse.<ProductItemResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy sản phẩm thành công!")
                .data(productService.getProductByProductVariant(variantId))
                .build();
    }

    @PatchMapping("/soft-delete/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Xoá mềm sản phẩm")
    public ApiResponse<?> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Xoá mềm sản phẩm thành công!")
                .build();
    }

    @PatchMapping("/restore/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Khôi phục sản phẩm")
    public ApiResponse<?> restoreProduct(@PathVariable Long productId) {
        productService.restoreProduct(productId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Khôi phục sản phẩm thành công!")
                .build();
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<Page<ProductItemResponse>> getProductsByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<Page<ProductItemResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm theo danh mục thành công!")
                .data(productService.getAllProductsByCategoryId(categoryId, page, size))
                .build();
    }

    @GetMapping("/new-arrival")
    public ApiResponse<Page<ProductItemResponse>> getNewArrivalProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<Page<ProductItemResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm mới nhất thành công!")
                .data(productService.getAllProductsByNewArrival(page, size))
                .build();
    }
    @GetMapping("/best-seller")
    public ApiResponse<Page<ProductItemResponse>> getBestSellerProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.<Page<ProductItemResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm bán chạy nhất thành công!")
                .data(productService.getAllProductsByBestSeller(page, size))
                .build();
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lấy tất cả sản phẩm cho admin")
    public ApiResponse<Page<ProductResponse>> getAllProductsByAdmin(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection
    ) {
        return ApiResponse.<Page<ProductResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách sản phẩm thành công!")
                .data(productService.getAllProductsByAdmin(search, page, size, sortBy, sortDirection))
                .build();
    }

}
