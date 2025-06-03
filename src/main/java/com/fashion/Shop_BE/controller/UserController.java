package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.RequestAddUser;
import com.fashion.Shop_BE.dto.request.RequestUpdateUser;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.UserResponse;
import com.fashion.Shop_BE.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Tag(name="User", description = "USER API")
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    @Operation(summary = "Lấy thông tin cá nhân")
    public ApiResponse<UserResponse> getProfile() {
        UserResponse userResponse = userService.getProfile();
        return ApiResponse.<UserResponse>builder()
                .data(userResponse)
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin cá nhân thành công")
                .build();
    }
    @PutMapping("/update-profile")
    @Operation(summary = "Cập nhật thông tin cá nhân")
    public ApiResponse<UserResponse> updateProfile(@Valid @ModelAttribute RequestUpdateUser request){
        UserResponse userResponse=userService.updateProfile(request);
        return ApiResponse.<UserResponse>builder()
                .data(userResponse)
                .code(HttpStatus.OK.value())
                .message("Cập nhật thông tin cá nhân thành công!")
                .build();
    }
    @PutMapping("/{userId}")
    @Operation(summary = "Cập nhật thông tin cá nhân")
    public ApiResponse<UserResponse> updateUser(@PathVariable Long userId,@Valid @RequestBody RequestAddUser request){
        UserResponse userResponse=userService.updateUser(userId, request);
        return ApiResponse.<UserResponse>builder()
                .data(userResponse)
                .code(HttpStatus.OK.value())
                .message("Cập nhật thông tin cá nhân thành công!")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping()
    @Operation(summary = "Thêm người dùng")
    public ApiResponse<UserResponse> addUser(@Valid @RequestBody RequestAddUser request){
        UserResponse userResponse=userService.addUser(request);
        return ApiResponse.<UserResponse>builder()
                .data(userResponse)
                .code(HttpStatus.CREATED.value())
                .message("Thêm người dùng thành công!")
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "Lấy danh sách người dùng")
    public ApiResponse<Page<UserResponse>> getAllUsers(
            @RequestParam(required = false, defaultValue = "") String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ) {
        Page<UserResponse> userResponses=userService.getAllUsers(search,page,size,sortBy,sortDirection);
        return ApiResponse.<Page<UserResponse>>builder()
                .data(userResponses)
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách nguời dùng thành công!")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/by-role")
    @Operation(summary = "Lấy danh sách người dùng theo role")
    public ApiResponse<Page<UserResponse>> getUsersByRole(
            @RequestParam String roleName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Page<UserResponse> userResponses=userService.getUsersByRole(roleName,page,size);
        return ApiResponse.<Page<UserResponse>>builder()
                .data(userResponses)
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách theo role thành công!")
                .build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    @Operation(summary = "Xoá tài khoản")
    public ApiResponse<?> deleteUser(@PathVariable Long userId){
        userService.deleteUserById(userId);
        return ApiResponse.builder()
                .message("Xoá tài khoản thành công!")
                .code(HttpStatus.OK.value())
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/restore/{userId}")
    @Operation(summary = "Khôi phục tài khoản")
    public ApiResponse<?> restoreUser(@PathVariable Long userId){
        userService.restoreUserById(userId);
        return ApiResponse.builder()
                .message("Khỏi phục tài khoản thành công!")
                .code(HttpStatus.OK.value())
                .build();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/add-role")
    @Operation(summary = "Thêm role cho người dùng")
    public ApiResponse<UserResponse> addRole(@PathVariable Long userId,@RequestParam String roleName){
        return ApiResponse.<UserResponse>builder()
                .data(userService.addRoleToUser(userId,roleName))
                .code(HttpStatus.OK.value())
                .message("Thêm role cho user thành công!")
                .build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/remove-role")
    @Operation(summary = "Xoá role cho người dùng")
    public ApiResponse<UserResponse> removeRole(@PathVariable Long userId,@RequestParam String roleName){
        return ApiResponse.<UserResponse>builder()
                .data(userService.removeRoleFromUser(userId,roleName))
                .code(HttpStatus.OK.value())
                .message("Xoá role từ user thành công!")
                .build();
    }

}
