package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestOrder;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.OrderResponse;
import com.fashion.Shop_BE.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name="Order", description = "Quản lý đơn hàng")
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    @Operation(summary = "Đặt hàng")
    public ApiResponse<?> placeOrder(@Valid @RequestBody RequestOrder requestOrder) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Đặt hàng thành công")
                .data(orderService.placeOrder(requestOrder).getBody())
                .build();
    }
    @GetMapping()
    @Operation(summary = "Lấy danh sách đơn hàng")
    public ApiResponse<Page<OrderResponse>> getAllOrderByUser(@RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "orderId") String sortBy,
                                                              @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách đơn hàng thành công")
                .data(orderService.getAllOrderByUser(page,size,sortBy,sortDirection))
                .build();
    }
    @GetMapping("/status/{status}")
    @Operation(summary = "Lấy danh sách đơn hàng theo trạng thái")
    public ApiResponse<Page<OrderResponse>> getOrderByStatus(@PathVariable String status,
                                                             @RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size,
                                                             @RequestParam(defaultValue = "orderId") String sortBy,
                                                             @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách đơn hàng theo trạng thái thành công")
                .data(orderService.getOrderByStatus(status,page,size,sortBy,sortDirection))
                .build();
    }
    @GetMapping("/{orderId}")
    @Operation(summary = "Lấy thông tin đơn hàng theo id")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ApiResponse.<OrderResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy đơn hàng thành công")
                .data(orderService.getOrderById(orderId))
                .build();
    }
    @PatchMapping("/cancel/{orderId}")
    @Operation(summary = "Huỷ đơn hàng")
    public ApiResponse<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Huỷ đơn hàng thành công")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("update-status/{orderId}")
    @Operation(summary = "Cập nhật trạng thái đơn hàng")
    public ApiResponse<String> updateOrderStatus(@PathVariable Long orderId) {
        orderService.updateOrderStatus(orderId);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật trạng thái đơn hàng thành công")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    @Operation(summary = "Lấy danh sách đơn hàng cho admin")
    public ApiResponse<Page<OrderResponse>> getAllOrdersByAdmin(@RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size,
                                                                 @RequestParam(defaultValue = "orderId") String sortBy,
                                                                 @RequestParam(defaultValue = "DESC") String sortDirection) {
        return ApiResponse.<Page<OrderResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách đơn hàng cho admin thành công")
                .data(orderService.getAllOrdersByAdmin(page,size,sortBy,sortDirection))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/cancel/{orderId}")
    @Operation(summary = "Huỷ đơn hàng cho admin")
    public ApiResponse<String> adminCancelOrder(@PathVariable Long orderId) {
        orderService.adminCancelOrder(orderId);
        return ApiResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .message("Huỷ đơn hàng cho admin thành công")
                .build();
    }


}
