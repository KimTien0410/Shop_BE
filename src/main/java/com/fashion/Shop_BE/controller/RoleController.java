package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestAddPermissionRoRole;
import com.fashion.Shop_BE.dto.request.RequestRole;
import com.fashion.Shop_BE.dto.request.RequestUpdateRole;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.RoleResponse;
import com.fashion.Shop_BE.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
@Tag(name="Role", description = "Quản lý Role")
public class RoleController {
    private final RoleService roleService;

    @PostMapping()
    @Operation(summary = "Thêm mới Role")
    public ApiResponse<RoleResponse> addRole(@RequestBody RequestRole request){
        return ApiResponse.<RoleResponse>builder()
                .data(roleService.addRole(request))
                .message("Thêm mới Role thành công!")
                .code(HttpStatus.CREATED.value())
                .build();
    }
    @GetMapping("/{roleName}")
    @Operation(summary= "Lấy thông tin Role theo tên")
    public ApiResponse<RoleResponse> getRole(@PathVariable String roleName){
        return ApiResponse.<RoleResponse>builder()
                .message("Lấy role thành công!")
                .code(HttpStatus.OK.value())
                .data(roleService.getRoleById(roleName))
                .build();
    }
    @GetMapping()
    @Operation(summary= "Lấy danh sách Role")
    public ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách Role thành công!")
                .data(roleService.getAllRole())
                .build();
    }
    @PatchMapping("/{roleName}")
    @Operation(summary= "Cập nhật Role")
    public ApiResponse<RoleResponse> updateRole(@PathVariable String roleName,@Valid @RequestBody RequestUpdateRole request){
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Cập nhật role thành công!")
                .data(roleService.updateRole(roleName,request))
                .build();
    }

    @PatchMapping("/{roleName}/add-permissions")
    @Operation(summary= "Thêm quyền cho Role")
    public ApiResponse<RoleResponse> addPermissionToRole(@PathVariable String roleName,@Valid @RequestBody RequestAddPermissionRoRole request){
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thêm quyền cho role thành công!")
                .data(roleService.addPermissionToRole(roleName,request))
                .build();
    }
    @PatchMapping("/{roleName}/delete-permissions")
    @Operation(summary= "Xóa quyền cho Role")
    public ApiResponse<RoleResponse> deletePermissionFromRole(@PathVariable String roleName,@Valid @RequestBody RequestAddPermissionRoRole request){
        return ApiResponse.<RoleResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Thêm quyền cho role thành công!")
                .data(roleService.deletePermissionFromRole(roleName,request))
                .build();
    }

}
