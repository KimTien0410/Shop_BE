package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestReceiverAddress;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.ReceiverAddressResponse;
import com.fashion.Shop_BE.service.ReceiverAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receiver-addresses")
@RequiredArgsConstructor
@Tag(name="ReceiverAddress", description = "Quản lý địa chỉ nhận hàng")
public class ReceiverAddressController {
    private final ReceiverAddressService receiverAddressService;

    @PostMapping()
    @Operation(summary = "Thêm địa chỉ nhận hàng")
    public ApiResponse<ReceiverAddressResponse> add(@Valid @RequestBody RequestReceiverAddress request){
        return ApiResponse.<ReceiverAddressResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm địa chỉ nhận hàng thành công!")
                .data(receiverAddressService.add(request))
                .build();
    }
    @GetMapping()
    @Operation(summary = "Lấy tất cả địa chỉ nhận hàng")
    public ApiResponse<List<ReceiverAddressResponse>> getAll(){
        return ApiResponse.<List<ReceiverAddressResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy tất cả địa chỉ nhận hàng!")
                .data(receiverAddressService.getAll())
                .build();
    }
    @GetMapping("/{receiverAddressId}")
    @Operation(summary = "Lấy địa chỉ nhận hàng theo id")
    public ApiResponse<ReceiverAddressResponse> getById(@PathVariable Long receiverAddressId){
        return ApiResponse.<ReceiverAddressResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy địa chỉ thành công!")
                .data(receiverAddressService.getById(receiverAddressId))
                .build();
    }
    @PutMapping("/{receiverAddressId}")
    @Operation(summary = "Cập nhật địa chỉ nhận hàng")
    public ApiResponse<ReceiverAddressResponse> update(@PathVariable Long receiverAddressId,@Valid @RequestBody RequestReceiverAddress request){
        return ApiResponse.<ReceiverAddressResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật địa chỉ nhận hàng thành công!")
                .data(receiverAddressService.update(receiverAddressId, request))
                .build();
    }
    @DeleteMapping("/{receiverAddressId}")
    @Operation(summary = "Xoá địa chỉ nhận hàng")
    public ApiResponse<?> delete(@PathVariable Long receiverAddressId){
        receiverAddressService.delete(receiverAddressId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Xoá địa chỉ thành công!")
                .build();
    }

    @PatchMapping("/set-default/{receiverAddressId}")
    @Operation(summary = "Đặt địa chỉ nhận hàng mặc định")
    public ApiResponse<?> setDefault(@PathVariable Long receiverAddressId){
        receiverAddressService.setDefault(receiverAddressId);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Đặt địa chỉ mặc định thành công!")
                .build();
    }
    @GetMapping("/default")
    @Operation(summary = "Lấy địa chỉ nhận hàng mặc định")
    public ApiResponse<ReceiverAddressResponse> getDefault(){
        return ApiResponse.<ReceiverAddressResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy địa chỉ mặc định thành công!")
                .data(receiverAddressService.getDefault())
                .build();
    }
}
