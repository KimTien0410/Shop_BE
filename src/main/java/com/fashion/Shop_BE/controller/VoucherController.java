package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestVoucher;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.VoucherResponse;
import com.fashion.Shop_BE.entity.User;
import com.fashion.Shop_BE.service.UserService;
import com.fashion.Shop_BE.service.VoucherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
@Tag(name="Voucher", description = "Quản lý voucher")
public class VoucherController {
    private final VoucherService voucherService;
    private final UserService userService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Thêm mới voucher")
    public ApiResponse<VoucherResponse> add(@Valid @RequestBody RequestVoucher requestVoucher){
        return ApiResponse.<VoucherResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Thêm mới voucher thành công!")
                .data(voucherService.addVoucher(requestVoucher))
                .build();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin chi tiết voucher")
    public ApiResponse<VoucherResponse> getVoucherById(@PathVariable Long id){
        return ApiResponse.<VoucherResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin chi tiết voucher thành công!")
                .data(voucherService.getVoucherById(id))
                .build();
    }

    @GetMapping("/code/{voucherCode}")
    @Operation(summary = "Lấy thông tin voucher theo mã voucher")
    public ApiResponse<VoucherResponse> getVoucherByCode(@PathVariable String voucherCode){
        return ApiResponse.<VoucherResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin voucher thành công!")
                .data(voucherService.getVoucherByCode(voucherCode))
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    @Operation(summary = "Lấy danh sách voucher")
    public ApiResponse<List<VoucherResponse>> getAllVouchers(){
        return ApiResponse.<List<VoucherResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách voucher thành công!")
                .data(voucherService.getAllVouchers())
                .build();
    }

    @GetMapping()
    @Operation(summary = "Lấy danh sách voucher ")
    public ApiResponse<List<VoucherResponse>> getAllVouchersByUser(){
        return ApiResponse.<List<VoucherResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách voucher thành công!")
                .data(voucherService.getActiveVouchers())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Cập nhật voucher")
    public ApiResponse<VoucherResponse> updateVoucher(@PathVariable Long id,@Valid @RequestBody RequestVoucher requestVoucher){
        return ApiResponse.<VoucherResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhậy voucher thành công!")
                .data(voucherService.updateVoucher(id, requestVoucher))
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Xoá voucher")
    public ApiResponse<?> deleteVoucher(@PathVariable Long id){
        voucherService.deleteVoucher(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Xoá voucher thành công!")
                .build();
    }
    @PostMapping("/apply/{voucherCode}")
    @Operation(summary = "Áp dụng voucher")
    public ApiResponse<?> applyVoucher(@PathVariable String voucherCode,@Valid @RequestBody Double orderValue){
        User user=userService.getUserAuthenticated();
        double result =voucherService.applyVoucher(user.getUserId(),voucherCode,orderValue);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .data(result)
                .build();
    }
}
