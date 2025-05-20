package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestDeliveryMethod;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.DeliveryMethodResponse;
import com.fashion.Shop_BE.service.DeliveryMethodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/delivery-methods")
@RequiredArgsConstructor
@Tag(name="DeliveryMethod", description = "Quản lý phương thức giao hàng")
public class DeliveryMethodController {
    private final DeliveryMethodService deliveryMethodService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary="Thêm phương thức giao hàng")
    public ApiResponse<DeliveryMethodResponse> add(@RequestPart("deliveryMethod") RequestDeliveryMethod deliveryMethod,
                                                   @RequestPart(value = "logo",required = false)MultipartFile file) {
        RequestDeliveryMethod requestDeliveryMethod=RequestDeliveryMethod.builder()
                .deliveryMethodName(deliveryMethod.getDeliveryMethodName())
                .deliveryFee(deliveryMethod.getDeliveryFee())
                .deliveryMethodDescription(deliveryMethod.getDeliveryMethodDescription())
                .deliveryMethodLogo(file)
                .build();
        return ApiResponse.<DeliveryMethodResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm phương thức thanh toán thành công!")
                .data(deliveryMethodService.add(requestDeliveryMethod))
                .build();
    }
    @GetMapping
    @Operation(summary="Lấy danh sách phương thức giao hàng")
    public ApiResponse<List<DeliveryMethodResponse>> getAll() {
        return ApiResponse.<List<DeliveryMethodResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phương thức giao hàng thành công!")
                .data(deliveryMethodService.getAll())
                .build();
    }
    @GetMapping("/{deliveryMethodId}")
    @Operation(summary="Lấy phương thức giao hàng theo id")
    public ApiResponse<DeliveryMethodResponse> getById(@PathVariable Long deliveryMethodId) {
        return ApiResponse.<DeliveryMethodResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy phương thức giao hàng thành công!")
                .data(deliveryMethodService.getById(deliveryMethodId))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{deliveryMethodId}")
    @Operation(summary="Cập nhật phương thức giao hàng")
    public ApiResponse<DeliveryMethodResponse> update(@PathVariable Long deliveryMethodId,
                                                      @RequestPart("deliveryMethod") RequestDeliveryMethod deliveryMethod,
                                                      @RequestPart(value = "logo",required = false)MultipartFile file) {
        RequestDeliveryMethod requestDeliveryMethod=RequestDeliveryMethod.builder()
                .deliveryMethodName(deliveryMethod.getDeliveryMethodName())
                .deliveryFee(deliveryMethod.getDeliveryFee())
                .deliveryMethodDescription(deliveryMethod.getDeliveryMethodDescription())
                .deliveryMethodLogo(file)
                .build();
        return ApiResponse.<DeliveryMethodResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật phương thức giao hàng thành công!")
                .data(deliveryMethodService.update(deliveryMethodId, requestDeliveryMethod))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{deliveryMethodId}")
    @Operation(summary="Cập nhật trạng thái phương thức giao hàng")
    public ApiResponse<?> changeStatus(@PathVariable Long deliveryMethodId){
        deliveryMethodService.changeStatusMethod(deliveryMethodId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật trạng thái thành công!")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{deliveryMethodId}")
    @Operation(summary="Xoá phương thức giao hàng")
    public ApiResponse<?> delete(@PathVariable Long deliveryMethodId) {
        deliveryMethodService.delete(deliveryMethodId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Xoá phương thức giao hàng thành công!")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Operation(summary="Admin Lấy danh sách phương thức giao hàng")
    public ApiResponse<List<DeliveryMethodResponse>> getAllByAdmin() {
        return ApiResponse.<List<DeliveryMethodResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phương thức giao hàng thành công!")
                .data(deliveryMethodService.getAllByAdmin())
                .build();
    }

}
