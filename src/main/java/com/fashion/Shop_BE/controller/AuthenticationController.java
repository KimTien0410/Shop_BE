package com.fashion.Shop_BE.controller;

import com.fashion.Shop_BE.dto.request.*;
import com.fashion.Shop_BE.dto.response.ApiResponse;
import com.fashion.Shop_BE.dto.response.AuthenticationResponse;
import com.fashion.Shop_BE.dto.response.IntrorespectResponse;
import com.fashion.Shop_BE.dto.response.UserResponse;
import com.fashion.Shop_BE.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name="Authentication", description = "Authentication API")
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/register")
    @Operation(summary = "Đăng ký tài khoản")
    public ApiResponse<?> register(@Valid @RequestBody RequestRegister request){
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(authenticationService.register(request))
                .build();
    }
    @GetMapping("/verify")
    @Operation(summary = "Xác thực tài khoản")
    public ApiResponse<?> verify(@RequestParam String token){
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message(authenticationService.verifyEmail(token))
                .build();
    }
    @PostMapping("/resend-verification")
    @Operation(summary = "Gửi lại mã xác thực")
    public ApiResponse<?> resendVerification(@Valid @RequestBody RequestResendVerification request){
       return ApiResponse.builder()
               .code(HttpStatus.OK.value())
               .message(authenticationService.resendVerificationCode(request))
               .build();
    }
    @PostMapping("/login")
    @Operation(summary = "Đăng nhập")
    public ApiResponse<AuthenticationResponse> authenticate(@Valid @RequestBody RequestAuthentication requestAuthentication){
        AuthenticationResponse data = authenticationService.authenticate(requestAuthentication);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Đăng nhập thành công!")
                .data(data).build();
    }
    @PostMapping("/logout")
    @Operation(summary = "Đăng xuất")
    public ApiResponse<?> logout(@RequestHeader("Authorization") String token){
        String jwtToken=token.replace("Bearer ", "");
        authenticationService.logout(jwtToken);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Đăng xuất thành công")
                .build();
    }
    @PostMapping("/introspect")
    public ApiResponse<IntrorespectResponse> introrespect(@Valid @RequestBody IntrospectRequest introspectRequest){
        var result=authenticationService.introrespect(introspectRequest);
        return ApiResponse.<IntrorespectResponse>builder()
                .data(result)
                .code(HttpStatus.OK.value())
                .message("Introrespect successful")
                .build();
    }
    @PostMapping("/refresh-token")
    @Operation(summary = "Làm mới token")
    public ApiResponse<AuthenticationResponse> refreshToken(@Valid @RequestBody RequestRefreshToken request){
        AuthenticationResponse data = authenticationService.refreshToken(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .data(data).build();
    }


}
