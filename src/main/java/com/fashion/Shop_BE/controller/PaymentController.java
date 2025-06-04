package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.service.PayOSService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "API thanh toán")
public class PaymentController {
    private final PayOSService payOSService;

    @PostMapping("/confirm-webhook")
    public ApiResponse<?> confirmWebhook(@RequestBody ObjectNode webhookBody) {
        System.out.println(webhookBody);
       // payOSService.verifyWebhook(webhookBody);
        return ApiResponse.builder()
                .code(200)
                .message("Xác thực webhook thành công")
                .build();
    }
    @Operation(summary = "Huỷ link thanh toán")
    @PostMapping("/cancel/{orderId}")
    public ApiResponse<?> cancelPaymentLink(@PathVariable Long orderId) {
        payOSService.cancelPaymentLink(orderId);
        return ApiResponse.builder()
                .code(200)
                .message("Hủy thanh toán thành công")
                .build();
    }
    @GetMapping("/get-payment-url/{orderId}")
    @Operation(summary = "Lấy URL thanh toán")
    public ApiResponse<?> getPaymentUrl(@PathVariable Long orderId) {
        String url = payOSService.getPaymentUrl(orderId);
        return ApiResponse.builder()
                .code(200)
                .message("Lấy URL thanh toán thành công")
                .data(url)
                .build();
    }
}
