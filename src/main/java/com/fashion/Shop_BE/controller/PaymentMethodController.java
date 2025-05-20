package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestPaymentMethod;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.PaymentMethodResponse;
import com.fashion.Shop_BE.service.PaymentMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/payment-methods")
@RequiredArgsConstructor
@Tag(name="PaymentMethod", description = "Quản lý phương thức thanh toán")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    @Operation(summary = "Thêm phương thức thanh toán")
    public ApiResponse<PaymentMethodResponse> add(@RequestPart(name ="paymentMethodName") String paymentMethodName,
                                                  @RequestPart(name="logo",required = false) MultipartFile logo) {
        RequestPaymentMethod requestPaymentMethod = new RequestPaymentMethod(paymentMethodName, logo);
        return ApiResponse.<PaymentMethodResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thêm phương thức thanh toán thành công!")
                .data(paymentMethodService.save(requestPaymentMethod))
                .build();
    }
    @GetMapping()
    @Operation(summary = "Lấy danh sách phương thức thanh toán")
    public ApiResponse<List<PaymentMethodResponse>> getAll() {
        return ApiResponse.<List<PaymentMethodResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phương thức thanh toán thành công!")
                .data(paymentMethodService.getAll())
                .build();
    }
    @GetMapping("/{paymentMethodId}")
    @Operation(summary = "Lấy phương thức thanh toán theo id")
    public ApiResponse<PaymentMethodResponse> getById(@PathVariable Long paymentMethodId) {
        return ApiResponse.<PaymentMethodResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy phương thức thanh toán thành công!")
                .data(paymentMethodService.getById(paymentMethodId))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{paymentMethodId}")
    @Operation(summary = "Cập nhật phương thức thanh toán")
    public ApiResponse<PaymentMethodResponse> update(@PathVariable Long paymentMethodId,
                                                     @RequestPart(name="paymentMethodName") String paymentMethodName,
                                                     @RequestPart(name="logo",required = false) MultipartFile logo) {
        RequestPaymentMethod requestPaymentMethod=RequestPaymentMethod.builder()
                .paymentMethodName(paymentMethodName)
                .paymentMethodLogo(logo)
                .build();
        return ApiResponse.<PaymentMethodResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật phương thức thanh toán thành công")
                .data(paymentMethodService.update(paymentMethodId, requestPaymentMethod))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "Admin Lấy tất cả phương thức thanh toán")
    public ApiResponse<List<PaymentMethodResponse>> getAllByAdmin(){
        return ApiResponse.<List<PaymentMethodResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phương thức thanh toán thành công!")
                .data(paymentMethodService.getAllByAdmin())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{paymentMethodId}")
    @Operation(summary = "Cập nhật trạng thái phương thức thanh toán")
    public ApiResponse<?> changeStatus(@PathVariable Long paymentMethodId){
        paymentMethodService.changeStatusMethod(paymentMethodId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật trạng thái thành công!")
                .build();
    }
}
