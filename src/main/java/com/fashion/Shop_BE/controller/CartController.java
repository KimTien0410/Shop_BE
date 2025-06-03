package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestCart;
import com.fashion.Shop_BE.dto.request.RequestProductVariantId;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.CartResponse;
import com.fashion.Shop_BE.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Tag(name="Cart", description = "API giỏ hàng")
public class CartController {
    private final CartService cartService;

    @GetMapping()
    @Operation(summary = "Lấy giỏ hàng của người dùng")
    public ApiResponse<CartResponse> getCartByUser() {
        return ApiResponse.<CartResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy giỏ hàng thành công!")
                .data(cartService.getCartByUser())
                .build();
    }
    @PostMapping()
    @Operation(summary = "Thêm sản phẩm vào giỏ hàng")
    public ApiResponse<CartResponse> addToCart(@RequestBody RequestCart requestAddCart) {
        return ApiResponse.<CartResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm sản phẩm vào giỏ hàng thành công!")
                .data(cartService.addToCart(requestAddCart))
                .build();
    }

    @DeleteMapping()
    @Operation(summary = "Xoá sản phẩm khỏi giỏ hàng")
    public ApiResponse<?> removeFromCart(@RequestBody RequestProductVariantId productVariantIds) {
        cartService.removeMultipleProductsFromCart(productVariantIds);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Xoá sản phẩm khỏi giỏ hàng thành công!")
                .build();
    }
    @PutMapping()
    @Operation(summary = "Cập nhật giỏ hàng")
    public ApiResponse<CartResponse> updateCartDetail(@RequestBody RequestCart requestUpdateCart) {
        return ApiResponse.<CartResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật giỏ hàng thành công!")
                .data(cartService.updateCartDetail(requestUpdateCart))
                .build();
    }

}
