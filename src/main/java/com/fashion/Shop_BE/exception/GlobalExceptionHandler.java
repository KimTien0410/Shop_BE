package com.fashion.Shop_BE.exception;

import com.fashion.Shop_BE.dto.response.ApiResponse;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiResponse> handlingRuntimeExcception(RuntimeException exception) {
        ApiResponse apiResponse = ApiResponse.builder()
                .code(HttpStatus.NOT_FOUND.value())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.badRequest().body(apiResponse);
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String fieldName = ex.getName(); // Lấy tên trường bị lỗi
        String invalidValue = ex.getValue() != null ? ex.getValue().toString() : "null"; // Giá trị bị lỗi
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown"; // Kiểu dữ liệu mong muốn

        String errorMessage = String.format("Giá trị '%s' không hợp lệ cho trường '%s'. Cần nhập kiểu %s.",
                invalidValue, fieldName, expectedType);

        ApiResponse response = ApiResponse.builder()
                .code(ErrorCode.INVALID_TYPE.getCode())
                .message(errorMessage)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingValidException(MethodArgumentNotValidException exception) {
        Map<String, Object> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();

            try {
                // Nếu message là Enum Key, lấy message từ Enum ErrorCode
                ErrorCode errorCode = ErrorCode.valueOf(errorMessage);
                var constraintViolation =
                        error.unwrap(ConstraintViolation.class);
                Map<String, Object> attributes = constraintViolation.getConstraintDescriptor().getAttributes();

                errorMessage = Objects.nonNull(attributes)
                        ? mapAttribute(errorCode.getMessage(), attributes)
                        : errorCode.getMessage();
            } catch (IllegalArgumentException ex) {
                // Không làm gì nếu không phải Enum
            }

            errors.put(fieldName, errorMessage);
        });

        ApiResponse apiResponse = ApiResponse.builder()
                .code(ErrorCode.INVALID_KEY.getCode()) // Có thể thay đổi mã lỗi chung
                .message("Dữ liệu không hợp lệ")
                .data(errors) // Trả về danh sách lỗi
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
//        String enumKey = exception.getFieldError().getDefaultMessage();
//
//        ErrorCode errorCode = ErrorCode.INVALID_KEY;
//        Map<String, Object> attributes = null;
//        try {
//            errorCode = ErrorCode.valueOf(enumKey);
//            var constraintViolation =
//                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);
//            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
//            // log.info(attributes.toString());
//        } catch (IllegalArgumentException ex) {
//
//        }
//        ApiResponse apiResponse = ApiResponse.builder()
//                .code(errorCode.getCode())
//                .message(
//                        Objects.nonNull(attributes)
//                                ? mapAttribute(errorCode.getMessage(), attributes)
//                                : errorCode.getMessage())
//                .build();
//        return ResponseEntity.badRequest().body(apiResponse);
    }



    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.status(errorCode.getHttpStatus()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        return message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
    }

}
