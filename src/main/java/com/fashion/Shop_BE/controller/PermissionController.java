package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestPermission;
import com.fashion.Shop_BE.dto.request.RequestUpdatePermission;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.PermissionResponse;
import com.fashion.Shop_BE.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name="Permission", description = "Quản lý permission")
public class PermissionController {
    private final PermissionService permissionService;
    @PostMapping
    @Operation(summary = "Thêm mới permission")
    public ApiResponse<PermissionResponse> addPermission(@Valid @RequestBody RequestPermission request){
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.addPermission(request))
                .message("Thêm permission mới thành công!")
                .code(HttpStatus.CREATED.value())
                .build();
    }
    @GetMapping
    @Operation(summary = "Lấy danh sách permission")
    public ApiResponse<List<PermissionResponse>> getAllPermission(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách permission thành công!")
                .data(permissionService.getAllPermissions())
                .build();
    }
    @PutMapping("/{permissionName}")
    @Operation(summary = "Cập nhật permission")
    public ApiResponse<PermissionResponse> updatePermission(@PathVariable String permissionName, @RequestBody RequestUpdatePermission request){
        return ApiResponse.<PermissionResponse>builder()
                .data(permissionService.updatePermission(permissionName,request))
                .code(HttpStatus.OK.value())
                .message("Cập nhật permission thành công")
                .build();
    }
    @GetMapping("/{permissionName}")
    @Operation(summary = "Lấy thông tin permission theo tên")
    public ApiResponse<PermissionResponse> getPermission(@PathVariable String permissionName){
        return ApiResponse.<PermissionResponse>builder()
                .message("Lấy permission thành công!")
                .code(HttpStatus.OK.value())
                .data(permissionService.getById(permissionName))
                .build();
    }
    @DeleteMapping("/{permissionName}")
    @Operation(summary = "Xoá permission")
    public ApiResponse<?> deletePermission(@PathVariable String permissionName){
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(permissionService.deletePermission(permissionName))
                .build();
    }
}
